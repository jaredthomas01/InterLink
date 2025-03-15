package com.example.InterLink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()  // Allow public access to all endpoints
                .anyRequest().authenticated()
                .and()
                .httpBasic();  // Enable Basic Authentication (optional)

        return http.build();
    }
}
