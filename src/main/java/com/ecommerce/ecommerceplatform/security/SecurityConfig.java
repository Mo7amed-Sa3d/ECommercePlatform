package com.ecommerce.ecommerceplatform.security;

import com.ecommerce.ecommerceplatform.security.jwt.JwtFilter;
import com.ecommerce.ecommerceplatform.service.user.UserDetailsServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImplementation userDetailsService;
    private final JwtFilter jwtFilter; // Add this

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (since we are using REST API)
                .csrf(AbstractHttpConfigurer::disable)
                // Enable CORS for React frontend
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        //Swagger
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        //mail
                        .requestMatchers("/api/mail/**").permitAll()
                        //Auth endpoints
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/logout").authenticated()
                        .requestMatchers("/api/auth/registerSeller").hasRole("ADMIN")
                        .requestMatchers("/api/auth/registerAdmin").hasRole("ADMIN")
                        //Brand endpoints
                        .requestMatchers(HttpMethod.GET,"/api/brand/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/brand").permitAll()
                        .requestMatchers(HttpMethod.GET,"/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/brand").hasRole("ADMIN")
                        //Category endpoints
                        .requestMatchers(HttpMethod.GET,"/api/categories").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/categories/*/products").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/categories").hasRole("ADMIN")
                        //User endpoints
                        .requestMatchers(HttpMethod.GET,"/api/users/").hasAnyRole("ADMIN","USER","SELLER")
                        .requestMatchers(HttpMethod.POST,"/api/users/addresses").hasAnyRole("ADMIN","USER","SELLER")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/addresses/*").hasAnyRole("ADMIN","USER","SELLER")
                        //Cart endpoints
                        .requestMatchers("/api/users/cart").hasAnyRole("ADMIN","USER","SELLER")
                        //Order endpoints
                        .requestMatchers("/api/users/orders").hasAnyRole("ADMIN","USER","SELLER")
                        //Product endpoints
                        .requestMatchers(HttpMethod.GET,"/api/products").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/images/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/products").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers(HttpMethod.POST,"/api/products/*/images").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers(HttpMethod.PUT,"/api/products/*").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers(HttpMethod.DELETE,"/api/products/*").hasAnyRole("ADMIN","SELLER")

                        .requestMatchers("/api/shipment/**").hasAnyRole("ADMIN","SELLER")
                        //other endpoints
                        .requestMatchers("/api/stripe-webhook").permitAll()
                        .anyRequest().authenticated()                // all other endpoints require auth
                )
                // Use Basic Authentication
                //.httpBasic(Customizer.withDefaults());
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // JWT is stateless
                // Add JWT filter before UsernamePasswordAuthenticationFilter
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
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
