package ru.khehelk.cityroutes.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.khehelk.cityroutes.authservice.repository.UserRepository;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> (org.springframework.security.core.userdetails.UserDetails) userRepository.findByUsername(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
