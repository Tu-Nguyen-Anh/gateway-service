package com.example.springproject.exception.base;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.springproject.constant.CommonConstants.*;

/**
 * Base exception class that extends RuntimeException. It provides a structured way to handle and represent exceptions
 * in the application, including fields for message, code, status, and additional parameters.
 */
@Data
public class BaseException extends RuntimeException {

  private String message;
  private String code;
  private int status;
  private Map<String, String> params;

  public BaseException() {
    this.status = DEFAULT_STATUS;
    this.code = BLANK_CONSTANT;
    this.message = BLANK_CONSTANT;
    this.params = new HashMap<>();
  }

  /**
   * Adds a parameter to the exception. If the params map is null, initializes it before adding the parameter.
   *
   * @param key   The key of the parameter.
   * @param value The value of the parameter.
   */
  public void addParam(String key, String value) {
    if (Objects.isNull(params)) {
      params = new HashMap<>();
    }
    params.put(key, value);
  }
}