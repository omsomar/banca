package com.bp.banca.exception;

public class FatalException extends RuntimeException {
    public FatalException(String message) {
        super(message);
    }
}
