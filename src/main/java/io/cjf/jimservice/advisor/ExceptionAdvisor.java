package io.cjf.jimservice.advisor;

import io.cjf.jimservice.constant.ErrConstant;
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
        exOutDTO.setCode(ex.getCode());
        exOutDTO.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exOutDTO);
    }

    @ExceptionHandler
    public ExOutDTO handleRuntimeException(RuntimeException ex) {
        ExOutDTO exOutDTO = new ExOutDTO();
        exOutDTO.setCode(ErrConstant.SERVER_ERR_CODE);
        exOutDTO.setMessage(ErrConstant.SERVER_ERR_MSG);
        return exOutDTO;
    }

}
