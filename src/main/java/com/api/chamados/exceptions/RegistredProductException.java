package com.api.chamados.exceptions;

@Deprecated(forRemoval = true)
public class RegistredProductException extends RuntimeException{
    public RegistredProductException() {
        super("Produto já registrado");
    }
}
