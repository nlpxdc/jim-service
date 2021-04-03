package io.cjf.jimservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientException extends Exception{

    public ClientException(String msg) {
        super(msg);
    }

}
