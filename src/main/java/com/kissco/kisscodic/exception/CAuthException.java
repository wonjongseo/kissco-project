package com.kissco.kisscodic.exception;

public class CAuthException extends RuntimeException{
    public CAuthException() {
        super();
    }

    public CAuthException(String message) {
        super(message);
    }

    public CAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public CAuthException(Throwable cause) {
        super(cause);
    }

    protected CAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
