package ru.khehelk.cityroutes.authservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.authservice.config.JwtTokenProvider;
import ru.khehelk.cityroutes.authservice.domain.User;
import ru.khehelk.cityroutes.authservice.repository.UserRepository;
import ru.khehelk.cityroutes.authservice.service.dto.AuthRequest;
import ru.khehelk.cityroutes.authservice.service.dto.AuthResponse;
import ru.khehelk.cityroutes.authservice.service.dto.RegisterRequest;
import ru.khehelk.cityroutes.domain.model.Role;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    @Transactional
    public void init() {
        try {
            if (userRepository.findByUsername("admin@admin.ru").isPresent()) {
                throw new IllegalArgumentException("Пользователь с таким email уже существует");
            }
            User user = new User();
            user.setUsername("admin@admin.ru");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        } catch (Exception ignored) {}
    }

    @Transactional
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.email()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUsername(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRole(Role.USER);

        userRepository.save(user);
        return new AuthResponse(jwtTokenProvider.generateToken(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse loginUser(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        return new AuthResponse(jwtTokenProvider.generateToken(userDetailsService.loadUserByUsername(authRequest.email())));
    }

}
