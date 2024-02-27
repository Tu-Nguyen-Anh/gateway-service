package com.example.springproject.security;

import com.example.springproject.entity.Permission;
import com.example.springproject.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static com.example.springproject.entity.Role.*;

/**
 * SecurityConfig class is responsible for configuring security settings for the entire application.
 * It includes JWT authentication, CORS configuration, authorization rules, password encoding, and authentication provider.
 */
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Creates and returns a JwtAuthenticationFilter bean.
     *
     * @return JwtAuthenticationFilter bean
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Defines a list of URLs to be excluded from security rules (white-listed).
     *
     * @return Array of white-listed URLs
     */
    private String[] whiteList() {
        return new String[]{
              "/api/auth/**",
              "/swagger-ui/**",
              "/v3/api-docs/**"
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(
                    authorize -> authorize.requestMatchers(whiteList()).permitAll()
                          .requestMatchers("/api/v1/users/get/{id}").hasAuthority(Permission.VIEW_USER_DETAILS.name())
                          .requestMatchers("/api/v1/users/update/{id}").hasAuthority(Permission.UPDATE_USER.name())
                          .requestMatchers("/api/v1/users/all").hasAuthority(Permission.VIEW_ALL_USERS.name())
                          .requestMatchers("/service1").hasAnyAuthority(Permission.VIEW_SERVICE1.name())

              )
              .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
              .build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) for the application.
     *
     * @return CorsConfigurationSource
     */
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Creates and returns a PasswordEncoder bean for password hashing.
     *
     * @return BCryptPasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates and returns a UserDetailsService bean.
     *
     * @return CustomUserDetailService bean
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    /**
     * Creates and returns an AuthenticationProvider bean using DaoAuthenticationProvider.
     *
     * @return AuthenticationProvider bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Creates and returns an AuthenticationManager bean.
     *
     * @param configuration AuthenticationConfiguration instance
     * @return AuthenticationManager bean
     * @throws Exception if an error occurs during authentication manager creation
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}