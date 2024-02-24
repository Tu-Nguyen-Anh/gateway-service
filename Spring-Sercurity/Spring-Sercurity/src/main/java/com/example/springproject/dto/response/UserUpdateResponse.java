package com.example.springproject.dto.response;

import com.example.springproject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateResponse {
  private String id;
  private String username;
  private String email;
  private String phone;
  private Role role;
  private Date dateOfBirth;
}
