package com.kissco.kisscodic.exception;

public class CUserException extends RuntimeException{

    public CUserException() {
        super();
    }

    public CUserException(String message) {
        super(message);
    }

    public CUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CUserException(Throwable cause) {
        super(cause);
    }

    protected CUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
