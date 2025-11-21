package com.ecommerce.ecommerceplatform.security;

import com.ecommerce.ecommerceplatform.service.user.UserDetailsServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImplementation userDetailsService;

    public SecurityConfig(UserDetailsServiceImplementation userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "SELECT email, password, true as enabled FROM user WHERE email = ?"
//        );
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "SELECT email, 'ROLE_USER' FROM user WHERE email = ?"
//        );
//        return jdbcUserDetailsManager;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Permit login page, login POST, and CSS
                        .requestMatchers("/**").permitAll()
                        // Role-based access
                        .anyRequest().authenticated()
                ).formLogin(form ->
                        form.loginPage("/login")
                                .defaultSuccessUrl("/login?success=true")
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
