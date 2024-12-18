package ru.khehelk.cityroutes.authservice;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.khehelk.cityroutes.authservice.config.JwtTokenProvider;
import ru.khehelk.cityroutes.authservice.config.UserDetailsConfig;
import ru.khehelk.cityroutes.authservice.config.properties.JwtProperties;
import ru.khehelk.cityroutes.authservice.domain.User;
import ru.khehelk.cityroutes.authservice.repository.UserRepository;
import ru.khehelk.cityroutes.authservice.service.UserService;
import ru.khehelk.cityroutes.authservice.service.dto.AuthRequest;
import ru.khehelk.cityroutes.authservice.service.dto.AuthResponse;
import ru.khehelk.cityroutes.authservice.service.dto.RegisterRequest;
import ru.khehelk.cityroutes.domain.model.Role;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(new JwtProperties(
        "YXNkYXNtYWxrc2dta3NlbmdlcmZhc2Rhc21hbGtzZ21rc2VuZ2VyZmFzZGFzbWFsa3NnbWtzZW5nZXJmYXNkYXNtYWxrc2dta3NlbmdlcmZhc2Rhc21hbGtzZ21rc2VuZ2VyZg==",
        100000000000000000L));

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(
            userRepository, passwordEncoder, jwtTokenProvider, authenticationManager, userDetailsService
        );
    }

    @Test
    void login() {
        User user = new User();
        user.setUsername("admin@admin.ru");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        AuthRequest request = new AuthRequest("admin@admin.ru", "password");
        AuthResponse response = userService.loginUser(request);
        assertThat(response.token()).isNotNull();
        assertThat(jwtTokenProvider.getClaimsFromJWT(response.token()).getSubject()).isEqualTo("admin@admin.ru");
        assertThat(jwtTokenProvider.getClaimsFromJWT(response.token())).containsEntry("roles", List.of(Role.ADMIN.name()));
    }

    @Test
    void register() {
        RegisterRequest request = new RegisterRequest("admin@admin.ru", "password");
        AuthResponse response = userService.registerUser(request);
        assertThat(response.token()).isNotNull();
        assertThat(jwtTokenProvider.getClaimsFromJWT(response.token()).getSubject()).isEqualTo("admin@admin.ru");
        assertThat(jwtTokenProvider.getClaimsFromJWT(response.token())).containsEntry("roles", List.of(Role.USER.name()));
    }

}
