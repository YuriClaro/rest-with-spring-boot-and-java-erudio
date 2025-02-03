package br.com.erudio.exception;

import br.com.erudio.exception.custom.PersonNotFoundException;
import br.com.erudio.exception.custom.UnsupportedMathOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedMathOperationException.class)
    public final ResponseEntity<ErrorResponse> handleUnsupportedMathOperationException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handlePersonNotFoundException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
