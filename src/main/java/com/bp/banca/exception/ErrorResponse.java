package com.bp.banca.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;

    public ErrorResponse(HttpStatus httpStatus, LocalDateTime timestamp, String message) {
        this.status = httpStatus;
        this.timestamp = timestamp;
        this.message = message;
    }
}
