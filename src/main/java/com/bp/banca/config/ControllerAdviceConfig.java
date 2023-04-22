package com.bp.banca.config;

import com.bp.banca.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler(InvalidTransactionTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidTransactionTypeException(InvalidTransactionTypeException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInsufficientFundsException(InsufficientFundsException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(ReportGenerationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleReportGenerationException(ReportGenerationException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleReportGenerationException(AccountNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(FatalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleReportGenerationException(FatalException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleReportGenerationException(CustomerNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleReportGenerationException(TransactionNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
    }
}
