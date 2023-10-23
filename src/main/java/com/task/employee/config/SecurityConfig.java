package com.task.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${security.api.username}")
    private String apiUserName;

    @Value("${security.api.password}")
    private String apiPassword;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET,"/api/v1/employee/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/employee").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/v1/employee").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/employee").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        User.UserBuilder users = User.builder();
        UserDetails user = users
                .username(apiUserName)
                .password("{noop}"+apiPassword)
                .authorities(Collections.emptyList())
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
