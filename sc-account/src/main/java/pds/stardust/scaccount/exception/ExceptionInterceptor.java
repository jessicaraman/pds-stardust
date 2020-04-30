package pds.stardust.scaccount.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ExceptionInterceptor : Redefines exception management to return a clean JSON message
 */
@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    /**
     * Generic behavior for every exception
     *
     * @param ex Triggered custom exception
     * @return JSON response entity describing the exception
     */
    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> handleAllExceptions(CustomException ex) {
        CustomExceptionSchema exceptionResponse = new CustomExceptionSchema(ex.getId(), ex.getMessage(), ex.getDetails());
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
