package com.api.chamados.exceptions;

@Deprecated(forRemoval = true)
public class RegistredUserException extends RuntimeException{

    public RegistredUserException() {
        super("Usuário já cadastrado");
    }
}
