package com.emc.belustyle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;


    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; use caution in production
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/css/**", "/js/**").permitAll()
                                .requestMatchers("/login", "/register").permitAll()
                                .requestMatchers("/home").hasAnyRole("CUSTOMER", "ADMIN", "STAFF")
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/api/auth/forgot-password").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService))
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/login?error=true"))
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource, PasswordEncoder passwordEncoder) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Query to retrieve user by username or email
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password_hash, `enable` FROM `user` WHERE username=?");

        // Query to retrieve authorities/roles by user
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, CONCAT('ROLE_', r.role_name) AS role " +
                        "FROM `user` u " +
                        "JOIN user_role r ON u.role_id = r.role_id " +
                        "WHERE username = ?"
        );


        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10); // Passwords should be encoded using bcrypt
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
