package br.com.henrique.services.exceptions;

public class EmailExistenteException extends RuntimeException {

    public EmailExistenteException(String msg) {
        super(msg);
    }

    public EmailExistenteException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
