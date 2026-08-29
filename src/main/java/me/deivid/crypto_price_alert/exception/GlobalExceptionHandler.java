package me.deivid.crypto_price_alert.exception;

import me.deivid.crypto_price_alert.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlertNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> userAlertNotFound(UserAlertNotFoundException excpt) {

        ErrorResponseDTO error = new ErrorResponseDTO(
            HttpStatus.NOT_FOUND.value(),
            excpt.getMessage(),
            excpt.getId()
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    };


}
