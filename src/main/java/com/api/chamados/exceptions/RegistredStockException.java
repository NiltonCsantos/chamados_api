package com.api.chamados.exceptions;

@Deprecated(forRemoval = true)
public class RegistredStockException extends RuntimeException{

    public RegistredStockException() {
        super("Estoque já cadastrado");
    }
}
