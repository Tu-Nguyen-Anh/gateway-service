package com.example.springproject.exception.base;


import static com.example.springproject.constant.ExceptionCode.CONFLICT_CODE;
import static com.example.springproject.exception.base.StatusConstants.CONFLICT;

/**
 * ConflictException is a type of exception commonly
 * used to indicate that there is a conflict or data collision between requests.
 */
public class ConflictException extends BaseException {
    public ConflictException(String id, String objectName) {
        setStatus(CONFLICT);
        setCode(CONFLICT_CODE);
        addParam("id", id);
        addParam("objectName", objectName);
    }

    public ConflictException() {
        setStatus(CONFLICT);
        setCode(CONFLICT_CODE);
    }
}

