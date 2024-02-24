package com.example.springproject.configuration;


import com.example.springproject.repository.UserRepository;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.MessageService;
import com.example.springproject.service.base.MessageServiceImpl;
import com.example.springproject.service.impl.JwtService;
import com.example.springproject.service.impl.UserServiceImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class define all service component in application
 */
@Configuration
public class ServiceConfiguration {
  @Bean
  public UserService userService(UserRepository repository, JwtService jwtService, PasswordEncoder passwordEncoder) {
    return new UserServiceImpl(repository, jwtService, passwordEncoder);
  }
  @Bean
  public MessageService messageService(MessageSource messageSource) {
    return new MessageServiceImpl(messageSource);
  }
}