package com.example.springproject.exception.base;
import static com.example.springproject.constant.ExceptionCode.USER_NOT_FOUND_CODE;

public class UserNotFoundException extends NotFoundException{
  public UserNotFoundException(){
    setCode(USER_NOT_FOUND_CODE);
  }
}
