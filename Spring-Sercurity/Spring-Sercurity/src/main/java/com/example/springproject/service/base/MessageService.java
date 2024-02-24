package com.example.springproject.service.base;

/**
 * Interface for a message service that retrieves messages based on a given code and language.
 */
public interface MessageService {

  /**
   * Retrieves a message for the given code and language.
   *
   * @param code     The code identifying the message.
   * @param language The language in which the message should be retrieved.
   * @return The message associated with the given code and language.
   */
  String getMessage(String code, String language);
}