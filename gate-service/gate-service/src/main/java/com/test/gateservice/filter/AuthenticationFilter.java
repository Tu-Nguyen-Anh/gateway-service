//package com.test.gateservice.filter;
//
//
//import com.test.gateservice.dto.AuthResponse;
//import com.test.gateservice.service.JwtTokenService;
//import io.jsonwebtoken.JwtException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.net.InetSocketAddress;
//import java.time.LocalDateTime;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//
//@Slf4j
//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//  private static final String MISSING_AUTHORIZATION_HEADER = "Missing authorization header";
//  public static final String TOKEN_TYPE = "Bearer ";
//  public static final String DEFAULT_LANGUAGE = "en";
//  public static final int NUMBER_OF_REQUEST_PER_MINUTE = 30;
//  public static final String UNKNOWN_HOST = "Unknown host";
//  private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
//  private final ConcurrentHashMap<String, LocalDateTime> requestTimestamps = new ConcurrentHashMap<>();
//  private final RouterValidator validator;
//  private final JwtTokenService jwtUtil;
//
//  public AuthenticationFilter(RouterValidator validator, JwtTokenService jwtUtil) {
//    super(Config.class);
//    this.validator = validator;
//    this.jwtUtil = jwtUtil;
//  }
//
//  @Override
//  public GatewayFilter apply(Config config) {
//    return (exchange, chain) -> {
//      String clientIp = getClientIp(exchange.getRequest().getRemoteAddress());
//      if (!isRateLimitAllowed(clientIp)) {
//        return handleRateLimitExceeded(exchange);
//      }
//
//      if (validator.isSecured.test(exchange.getRequest())) {
//        return handleAuthorization(exchange, chain);
//      }
//      return chain.filter(exchange);
//    };
//  }
//
//  private Mono<Void> handleAuthorization(ServerWebExchange exchange, GatewayFilterChain chain) {
//    HttpHeaders headers = exchange.getRequest().getHeaders();
//    if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
//      throw new IllegalArgumentException(MISSING_AUTHORIZATION_HEADER);
//    }
//
//    String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
//    if (authHeader != null && authHeader.startsWith(TOKEN_TYPE)) {
//      authHeader = authHeader.substring(7);
//    }
//
//    try {
//      AuthResponse authResponse = jwtUtil.validateToken(authHeader, DEFAULT_LANGUAGE);
//      log.debug("(authResponse) : {}", authResponse);
//    } catch (JwtException ex) {
//      log.error("JWT processing failed: {}", ex.getMessage());
//      return exchange.getResponse().setComplete();
//    }
//    return chain.filter(exchange);
//  }
//
//  private Mono<Void> handleRateLimitExceeded(ServerWebExchange exchange) {
//    ServerHttpResponse response = exchange.getResponse();
//    response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
//    return response.setComplete();
//  }
//
//  private String getClientIp(InetSocketAddress remoteAddress) {
//    if (remoteAddress != null) {
//      return remoteAddress.getAddress().getHostAddress();
//    }
//    return UNKNOWN_HOST;
//  }
//
//  private boolean isRateLimitAllowed(String clientIp) {
//    requestTimestamps.putIfAbsent(clientIp, LocalDateTime.now());
//    requestCounts.putIfAbsent(clientIp, new AtomicInteger(0));
//
//    LocalDateTime lastResetTime = requestTimestamps.get(clientIp);
//    AtomicInteger count = requestCounts.get(clientIp);
//
//    if (lastResetTime.plusMinutes(1).isBefore(LocalDateTime.now())) {
//      requestTimestamps.put(clientIp, LocalDateTime.now());
//      count.set(0);
//    }
//
//    return count.incrementAndGet() <= NUMBER_OF_REQUEST_PER_MINUTE;
//  }
//
//
//  public static class Config {
//  }
//}
//
