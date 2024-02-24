package com.example.springproject.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {

  /**
   * The password for the user.
   * Must not be null, not blank, and must meet certain complexity criteria.
   */
  @NotBlank
  @NotNull
  @Pattern(regexp = "^(?=.*[!@#$%^&*()-_+=])(?=.*[A-Z])(?=.*[0-9]).{6,}$", message = "Password must contains at least 1 uppercase character, 1 number, 1 special character and have at least 6 characters!")
  private String password;

  /**
   * The email address for the user.
   * Must not be null, not blank, and must be a valid email address.
   */
  @NotBlank
  @NotNull
  @Email
  private String email;

  /**
   * The phone number for the user.
   * Must meet a specific pattern if provided.
   */
  @Pattern(regexp = "^(\\+84|0)([0-9]{9,10})$")
  private String phone;
  /**
   * The date of birth for the user.
   */
  @Past
  private Date dateOfBirth;
}
