package io.cjf.jimservice.advisor;

import io.cjf.jimservice.dto.out.ExOutDTO;
import io.cjf.jimservice.exception.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler
    public ResponseEntity<ExOutDTO> handleClientException(ClientException ex) {
        ExOutDTO exOutDTO = new ExOutDTO();
        exOutDTO.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exOutDTO);
    }

}
