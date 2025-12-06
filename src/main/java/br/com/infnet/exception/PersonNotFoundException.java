package br.com.infnet.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String id) {
        super("Usuário com ID " + id + " não encontrado.");
    }
}
