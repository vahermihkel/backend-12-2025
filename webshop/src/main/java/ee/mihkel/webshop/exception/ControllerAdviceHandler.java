package ee.mihkel.webshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException ex){
        ErrorMessage error = new ErrorMessage();
        error.setMessage(ex.getMessage());
        error.setStatus(400);
        error.setDate(new Date());
        return ResponseEntity.status(400).body(error);
    }

}
