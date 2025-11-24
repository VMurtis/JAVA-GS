package br.com.fiap.gs.GS.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    // Construtor que aceita a mensagem de erro
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    // Construtor sem mensagem
    public EmailAlreadyExistsException() {
        super("O email fornecido já está cadastrado.");
    }
}