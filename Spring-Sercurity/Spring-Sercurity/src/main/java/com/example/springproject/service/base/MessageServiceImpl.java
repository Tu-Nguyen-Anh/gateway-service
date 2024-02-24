package com.example.springproject.service.base;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * Implementation of the MessageService interface that retrieves messages from a MessageSource.
 */
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
  private final MessageSource messageSource;

  public String getMessage(String code, String language) {
    try {
      return messageSource.getMessage(code, null, new Locale(language));
    } catch (Exception ex) {
      return code;
    }
  }
}