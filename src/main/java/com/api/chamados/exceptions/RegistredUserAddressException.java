package com.api.chamados.exceptions;

@Deprecated(forRemoval = true)
public class RegistredUserAddressException extends RuntimeException {

    public RegistredUserAddressException() {
        super("Esse endereço já está atribuido a este usuário.");
    }
}
