package com.example.springproject.controller.advice;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.exception.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.springproject.constant.ExceptionCode.*;

/**
 * Global exception handler for handling various types of exceptions in the application.
 */
@RestControllerAdvice
@ControllerAdvice
public class ExceptionHandlerAdvice {
  private final MessageSource messageSource;

  @Autowired
  public ExceptionHandlerAdvice(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Handles exceptions of type BaseException and its subclasses.
   *
   * @param ex      The BaseException or its subclass instance.
   * @param locale  The locale for response messages.
   * @return ResponseEntity containing a ResponseGeneral with the error details.
   */
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ResponseGeneral<Object>> handleBaseException(BaseException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatus()));
  }

  /**
   * Handles NotFoundException and returns a 404 Not Found response.
   *
   * @param ex      The NotFoundException instance.
   * @param locale  The locale for response messages.
   * @return ResponseEntity containing a ResponseGeneral with the error details.
   */
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ResponseGeneral<Object>> handleNotFoundException(NotFoundException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles BadRequestException and returns a 400 Bad Request response.
   *
   * @param ex      The BadRequestException instance.
   * @param locale  The locale for response messages.
   * @return ResponseEntity containing a ResponseGeneral with the error details.
   */
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ResponseGeneral<Object>> handleBadRequestException(BadRequestException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles ConflictException and returns a 409 Conflict response.
   *
   * @param ex      The ConflictException instance.
   * @param locale  The locale for response messages.
   * @return ResponseEntity containing a ResponseGeneral with the error details.
   */
  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ResponseGeneral<Object>> handleConflictException(ConflictException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  /**
   * Handles MethodArgumentNotValidException and returns a 400 Bad Request response.
   *
   * @param ex          The MethodArgumentNotValidException instance.
   * @return ResponseGeneral containing a Map of field errors with their corresponding error messages.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseGeneral<Map<String, String>> handleBindingException(MethodArgumentNotValidException ex){
    BindingResult bindingResult = ex.getBindingResult();
    Map<String, String> errors = new HashMap<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return ResponseGeneral.<Map<String, String>>of(400, "Error binding", errors);
  }

  /**
   * Handles DuplicateException and returns a 400 Bad Request response.
   *
   * @param ex          The DuplicateException instance.
   * @param locale      The locale for response messages.
   * @return ResponseEntity containing a ResponseGeneral with the error details.
   */
  @ExceptionHandler(DuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ResponseGeneral<Object>> handleDuplicateData(DuplicateException ex, Locale locale){
    String message = getMessage(ex.getCode(), locale, null);
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles BadCredentialsException and returns a 400 Bad Request response.
   *
   * @param ex      The BadCredentialsException instance.
   * @param locale  The locale for response messages.
   * @return ResponseEntity containing a ResponseGeneral with the error details.
   */
  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ResponseGeneral<Object>> handleBadCredentials(BadCredentialsException ex, Locale locale){
    String message = getMessage(BAD_CREDENTIALS_CODE, locale, null);
    ResponseGeneral<Object> response = ResponseGeneral.of(HttpStatus.BAD_REQUEST.value(), message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles generic RuntimeException and returns a 500 Internal Server Error response.
   *
   * @param locale  The locale for response messages.
   * @return ResponseEntity containing a ResponseGeneral with the error details.
   */
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseGeneral<Object>> handleGenericException(Locale locale) {
    String message = getMessage(GENERIC_CODE, locale, null);
    ResponseGeneral<Object> response = ResponseGeneral.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Utility method to retrieve localized error messages.
   *
   * @param code    The error code.
   * @param locale  The locale for response messages.
   * @param params  Additional parameters for message interpolation.
   * @return The localized error message.
   */
  private String getMessage(String code, Locale locale, Map<String, String> params) {
    try {
      return messageSource.getMessage(code, params != null ? params.values().toArray() : null, locale);
    } catch (Exception e) {
      return code;
    }
  }
}