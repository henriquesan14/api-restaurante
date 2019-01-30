package br.com.henrique.services.exceptions;

public class PasswordInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PasswordInvalidException(String msg) {
        super(msg);
    }

    public PasswordInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
