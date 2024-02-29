package com.example.springproject.service.impl;

import com.example.springproject.utils.DateUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.example.springproject.constant.CommonConstants.SECRET_KEY;
import static org.hibernate.validator.internal.util.CollectionHelper.newHashMap;

/**
 * JwtService class provides methods for handling JWT (JSON Web Token) creation, validation, and extraction of claims.
 * It includes functions for extracting the username, generating tokens, checking token validity, and more.
 */
@Slf4j
@Service
public class JwtService {
  public static final String CLAIMS = "claims";

  /**
   * Extracts the username from a JWT token.
   *
   * @param token JWT token
   * @return Extracted username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a specific claim from a JWT token using the provided resolver function.
   *
   * @param token         JWT token
   * @param claimResolver Resolver function for extracting a specific claim
   * @param <T>           Type of the extracted claim
   * @return Extracted claim value
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  /**
   * Extracts all claims from a JWT token.
   *
   * @param token JWT token
   * @return All extracted claims
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
          .setSigningKey(getSignKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
  }

  /**
   * Retrieves the signing key for JWT token validation.
   *
   * @return JWT signing key
   */
  private Key getSignKey() {
    byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyByte);
  }

  /**
   * Generates a JWT token for a user based on UserDetails.
   *
   * @param userDetails UserDetails of the user
   * @return Generated JWT token
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Generates a JWT token with additional claims for a user based on UserDetails.
   *
   * @param extractClaims Additional claims to include in the token
   * @param userDetails   UserDetails of the user
   * @return Generated JWT token
   */
  public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
    Map<String, Object> rolesClaim = new HashMap<>();
    List<String> permissions = userDetails.getAuthorities().stream().map(s -> s.getAuthority()).toList();
    rolesClaim.put("permission", permissions);
    return Jwts.builder()
          .setClaims(extractClaims)
          .setSubject(userDetails.getUsername())
          .setIssuedAt(new Date(DateUtils.getCurrentTimeMillis()))
          .setExpiration(new Date(DateUtils.getCurrentTimeMillis() + 1000 * 60 * 24))
          .signWith(getSignKey())
          .addClaims(rolesClaim)
          .compact();
  }

  /**
   * Extracts the expiration date from a JWT token.
   *
   * @param token JWT token
   * @return Expiration date of the token
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Checks if a JWT token is valid for a specific user based on UserDetails.
   *
   * @param token       JWT token
   * @param userDetails UserDetails of the user
   * @return True if the token is valid, false otherwise
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  public List<String> getPermissionFromToken(String token) {
    log.debug("(getRoleFromToken) start");
    return getClaimsMap(token);
  }

  private List<String> getClaimsMap(String token) {
    @SuppressWarnings("unchecked")
    List<String> claimsMap = (List<String>) extractAllClaims(token).get("permission");
    return claimsMap;
  }

  /**
   * Checks if a JWT token has expired.
   *
   * @param token JWT token
   * @return True if the token has expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

}
