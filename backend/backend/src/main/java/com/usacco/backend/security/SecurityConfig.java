package com.usacco.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration for the user management application.
 * Configures password encoding, CORS, and security rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the password encoder to use BCrypt.
     * 
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain with CORS and basic security settings.
     * 
     * @param http HttpSecurity object to configure
     * @return SecurityFilterChain instance
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for REST API and H2 console
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configure CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Configure authorization rules - H2 console must come first
            .authorizeHttpRequests(authz -> authz
                // Allow access to H2 console completely (must be first)
                .requestMatchers("/h2-console/**").permitAll()
                // Allow all requests to API endpoints (for now - can be restricted later)
                .requestMatchers("/api/**").permitAll()
                // Allow access to static resources
                .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                // Require authentication for all other requests
                .anyRequest().authenticated()
            )
            
            // Configure headers - disable frame options for H2 console
            .headers(headers -> headers
                .frameOptions().disable() // For H2 console
                .contentTypeOptions().disable() // For H2 console
                .httpStrictTransportSecurity(hstsConfig -> hstsConfig.disable()) // For H2 console
            );

        return http.build();
    }

    /**
     * Configures CORS settings to allow cross-origin requests.
     * 
     * @return CorsConfigurationSource instance
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow all origins (can be restricted in production)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Allow common HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Allow common headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Allow credentials
        configuration.setAllowCredentials(true);
        
        // Set max age for preflight requests
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
