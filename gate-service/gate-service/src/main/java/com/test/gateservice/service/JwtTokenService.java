package com.test.gateservice.service;

import com.test.gateservice.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class JwtTokenService {

  private final RestTemplate restTemplate;
  private static final String authServiceUrl = "http://localhost:8080/api/auth/validate";

  @Autowired
  public JwtTokenService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public AuthResponse validateToken(String token, String language) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authServiceUrl)
          .queryParam("token", token);
    log.info("token:{}", token);
    ResponseEntity<AuthResponse> response = restTemplate.getForEntity(
          builder.toUriString(),
          AuthResponse.class,
          token,
          language
    );
    return response.getBody();
  }

}



