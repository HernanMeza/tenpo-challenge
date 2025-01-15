package com.tenpo.transaction.exception;

import com.tenpo.transaction.dto.response.Header;
import com.tenpo.transaction.dto.response.TransactionErrorResponse;
import com.tenpo.transaction.enums.CustomResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalCustomHandlerException {

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<TransactionErrorResponse> handleValidationException(Exception ex) {

        log.error("Error en la petición: {}", ex.getMessage(), ex);

        // Crear la respuesta personalizada con los detalles de error
        TransactionErrorResponse response = TransactionErrorResponse.builder()
                .header(Header.builder()
                        .statusCode(CustomResponseStatus.WRONG_PETITION.getCode())
                        .description(CustomResponseStatus.WRONG_PETITION.getDescription())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<TransactionErrorResponse> handleRuntimeException(Exception ex) {

        log.error("Error en la petición: {}", ex.getMessage(), ex);

        TransactionErrorResponse response = TransactionErrorResponse.builder()
                .header(Header.builder()
                        .statusCode(CustomResponseStatus.ERROR.getCode())
                        .description(CustomResponseStatus.ERROR.getDescription())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public TransactionErrorResponse handleTooManyRequests() {

        log.error("Demasiadas peticiones para el usuario");

        return TransactionErrorResponse.builder()
                .header(Header.builder()
                        .statusCode(CustomResponseStatus.MAX_PETITIONS_PER_USER.getCode())
                        .description(CustomResponseStatus.MAX_PETITIONS_PER_USER.getDescription())
                        .build())
                .build();
    }

}
