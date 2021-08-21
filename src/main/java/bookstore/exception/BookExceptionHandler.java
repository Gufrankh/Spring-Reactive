package bookstore.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 05-08-2021 03:15
 */

@ControllerAdvice
@Slf4j
public class BookExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> nonFoundException(NotFoundException ex) {
        log.error("Exception caught in nonFoundException :  {0} ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}
