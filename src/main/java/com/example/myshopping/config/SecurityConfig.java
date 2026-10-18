package com.example.myshopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorizeRequest ->
                            authorizeRequest
                                    .requestMatchers("/auth/login/kako/**").permitAll()
                                    .anyRequest().authenticated()
                        )
                .csrf(csrf -> csrf.disable())
                .formLogin(form->form.disable())
                .httpBasic(basic->basic.disable());

        return http.build();

    }
}
