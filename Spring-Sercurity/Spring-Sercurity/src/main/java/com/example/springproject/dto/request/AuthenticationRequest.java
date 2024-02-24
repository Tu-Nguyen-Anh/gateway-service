package com.example.springproject.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the request for user authentication.
 * It contains fields for the username and password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    /**
     * The username provided for authentication.
     * Must not be null and should not be blank.
     */
    @NotNull
    @NotBlank
    private String username;

    /**
     * The password provided for authentication.
     * Must not be null and should not be blank.
     */
    @NotBlank
    @NotNull
    private String password;

}
