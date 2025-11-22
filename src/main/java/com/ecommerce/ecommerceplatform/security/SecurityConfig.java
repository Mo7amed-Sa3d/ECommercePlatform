package com.ecommerce.ecommerceplatform.security;

import com.ecommerce.ecommerceplatform.service.user.UserDetailsServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImplementation userDetailsService;

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
                // Disable CSRF (since we are using REST API)
                .csrf(AbstractHttpConfigurer::disable)
                // Enable CORS for React frontend
                .cors(Customizer.withDefaults())
                // Authorize requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/products/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole("USER","SELLER")

                        .requestMatchers(HttpMethod.POST,"/api/users/orders/checkout").hasAnyRole("USER","SELLER")

                        .requestMatchers(HttpMethod.POST,"/api/users/cart").hasAnyRole("USER","SELLER")

                        .requestMatchers("/api/orders/**").hasAnyRole("SELLER","USER")

                        .requestMatchers(HttpMethod.POST,"/api/products").hasRole("SELLER")
                        .requestMatchers(HttpMethod.DELETE,"/api/products").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST,"/api/categories").hasRole("SELLER")
                        .requestMatchers(HttpMethod.GET,"/api/categories").hasAnyRole("USER","SELLER")
                        .anyRequest().authenticated()                // all other endpoints require auth
                )
                // Use Basic Authentication
                .httpBasic(Customizer.withDefaults());

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
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React dev server
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

/*
const username = "user@example.com";
const password = "Password123!";
const token = btoa(`${username}:${password}`);

fetch("http://localhost:8080/api/products", {
  headers: {
    "Authorization": `Basic ${token}`
  }
})
.then(res => res.json())
.then(data => console.log(data));


 */
