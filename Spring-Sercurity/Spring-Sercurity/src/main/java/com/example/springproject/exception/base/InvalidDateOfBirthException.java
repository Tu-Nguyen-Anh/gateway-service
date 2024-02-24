package com.example.springproject.exception.base;


public class InvalidDateOfBirthException extends BadRequestException {
  public InvalidDateOfBirthException() {
    setCode("com.example.springproject.exception.base.InvalidDateOfBirthException");
  }
}
