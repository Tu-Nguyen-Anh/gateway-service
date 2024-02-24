package com.example.springproject.exception.base;

import lombok.Data;

/**
 * DuplicateException is a type of exception commonly
 * used to indicate that there is duplication of data of an object or resource in the system.
 */
@Data
public class DuplicateException extends BaseException {

    public DuplicateException(String code) {
        setCode(code);
        setStatus(StatusConstants.BAD_REQUEST);
    }

    ;
}
