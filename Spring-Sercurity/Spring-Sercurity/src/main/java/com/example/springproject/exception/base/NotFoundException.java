package com.example.springproject.exception.base;

import static com.example.springproject.constant.ExceptionCode.NOT_FOUND_CODE;

/**
 * NotFoundException is a type of exception commonly
 * used to indicate that a resource or object cannot be found.
 */
public class NotFoundException extends BaseException {
    public NotFoundException() {
        setCode(NOT_FOUND_CODE);
        setStatus(StatusConstants.NOT_FOUND);
    }
}
