package br.com.fiap.gs.GS.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    // Construtor que aceita a mensagem de erro
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }

    // Construtor sem mensagem
    public UsernameAlreadyExistsException() {
        super("O nome de usuário fornecido já existe.");
    }
}