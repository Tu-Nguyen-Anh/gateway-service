package com.example.springproject.exception.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class containing commonly used HTTP status constants for exception handling.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusConstants {

  public static final Integer NOT_FOUND = 404;
  public static final Integer CONFLICT = 409;
  public static final Integer BAD_REQUEST = 400;
}