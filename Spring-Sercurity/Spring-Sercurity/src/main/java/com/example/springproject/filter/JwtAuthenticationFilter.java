package com.example.springproject.filter;

import com.example.springproject.service.impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.springproject.constant.CommonConstants.BEARER_TOKEN_PREFIX;
import static com.example.springproject.constant.CommonConstants.BEARER_PREFIX_LENGTH;
import static com.example.springproject.constant.CommonConstants.AUTHORIZATION_HEADER;

/**
 * JwtAuthenticationFilter is a component responsible for filtering and processing JWT-based authentication.
 * It intercepts incoming requests, extracts JWT from the Authorization header, and validates the token.
 * If the token is valid, it sets up the authentication details in the Spring Security context.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Method to filter incoming HTTP requests, extract JWT, and process authentication.
     *
     * @param request     HttpServletRequest representing the incoming request.
     * @param response    HttpServletResponse representing the outgoing response.
     * @param filterChain FilterChain for continuing the filter chain.
     * @throws ServletException if an error occurs during the filter process.
     * @throws IOException      if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwt;
        final String userName;

        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(BEARER_PREFIX_LENGTH);
        userName = jwtService.extractUsername(jwt);
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
