package com.example.springproject.dto.response;

import com.example.springproject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object (DTO) representing the response for user details.
 * It contains fields for the user ID, username, email, phone number, user role, and an authentication token.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
  private String id;
  private String username;
  private String email;
  private String phone;
  private Role role;
  private String token;
  private Date dateOfBirth;

  public UserResponse(String id, String username, String email, String phone, Role role, Date dateOfBirth) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.phone = phone;
    this.role = role;
    this.dateOfBirth = dateOfBirth;
  }
}
