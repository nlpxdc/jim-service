package io.cjf.jimservice.exception;

public class ClientException extends Exception{

    public Integer code;

    public ClientException(String msg) {
        super(msg);
    }

    public ClientException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
