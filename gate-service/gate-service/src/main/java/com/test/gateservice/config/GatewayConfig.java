//package com.test.gateservice.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//  @Bean
//  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//    return builder.routes()
//          .route("login_route", r -> r
//                .path("/api/auth/login")
//                .uri("http://localhost:8080"))
//          .route("service1_route", r -> r
//                .path("/service1")
//                .uri("http://localhost:1111"))
//          .route("service2_route", r -> r
//                .path("/service2")
//                .uri("http://localhost:2222"))
////          .route("security_router", r -> r.path("/login")
////                .filters(f -> f.rewritePath("/login", "/api/auth/login"))
////                .uri("http://localhost:8080"))
//          .build();
//  }
//}
//
