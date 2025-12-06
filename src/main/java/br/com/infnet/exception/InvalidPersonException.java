package br.com.infnet.exception;

public class InvalidPersonException extends RuntimeException {
    public InvalidPersonException(String message) {
        super(message);
    }
}
