package com.example.springproject.service.impl;

import com.example.springproject.dto.request.AuthenticationRequest;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.AuthenticationResponse;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.entity.Role;
import com.example.springproject.entity.User;
import com.example.springproject.exception.base.DuplicateException;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.security.CustomUserDetail;
import com.example.springproject.utils.DateUtils;
import com.example.springproject.utils.MapperUtils;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.springproject.constant.ExceptionCode.DUPLICATE_CODE;
import static com.example.springproject.constant.ExceptionCode.DUPLICATE_USERNAME_CODE;

/**
 * AuthenticationService class provides the implementation for user registration and authentication operations.
 * It includes methods for user registration, login, and checking for duplicate usernames.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  /**
   * Registers a new user with the provided information.
   * Performs username duplication check and encodes the password.
   * Generates a JWT token for the registered user.
   *
   * @param dto UserRequest containing user information
   * @return UserResponse with registered user details and JWT token
   */
  @Transactional
  public UserResponse register(UserRequest dto) {
    checkUsernameIfExist(dto.getUsername());
    User user = MapperUtils.toEntity(dto, User.class);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setCreatedAt(DateUtils.getCurrentTimeMillis());
    user.setRole(Role.USER);
    userRepository.save(user);
    return new UserResponse(
          user.getId(),
          user.getUsername(),
          user.getEmail(),
          user.getPhone(),
          user.getRole(),
          jwtService.generateToken(new CustomUserDetail(user)),
          user.getDateOfBirth()
    );
  }

  /**
   * Performs user authentication using the provided credentials.
   * Generates a JWT token for the authenticated user.
   *
   * @param authenticationRequest AuthenticationRequest containing user credentials
   * @return AuthenticationResponse with user ID and JWT token
   */
  public AuthenticationResponse logIn(AuthenticationRequest authenticationRequest) {
    var token = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    CustomUserDetail customUserDetail = (CustomUserDetail) authenticationManager.authenticate(token).getPrincipal();
    return AuthenticationResponse.builder()
          .id(customUserDetail.getUser().getId())
          .token(jwtService.generateToken(customUserDetail))
          .build();
  }

  /**
   * Checks if the provided username already exists in the database.
   * Throws DuplicateException if a duplicate username is found.
   *
   * @param username Username to check for duplication
   */
  private void checkUsernameIfExist(String username) {
    Optional<User> optionalUser = userRepository.findUserByUsername(username);
    log.info("(checkUsernameIfExist) {}", username);
    if (optionalUser.isPresent()) {
      throw new DuplicateException(DUPLICATE_USERNAME_CODE);
    }
  }
}
