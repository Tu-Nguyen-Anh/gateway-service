package com.example.springproject.exception.base;

import static com.example.springproject.constant.ExceptionCode.BAD_REQUEST_CODE;

/**
 * BadRequestException is a type of exception commonly
 * to indicate that a client's request to a server is malformed or invalid
 */
public class BadRequestException extends BaseException {
    public BadRequestException() {
        setCode(BAD_REQUEST_CODE);
        setStatus(StatusConstants.BAD_REQUEST);
    }
}