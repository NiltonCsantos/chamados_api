package com.api.chamados.exceptions;

@Deprecated(forRemoval = true)
public class QuantidadeDeProdutosInsuficenteException extends RuntimeException{
    public QuantidadeDeProdutosInsuficenteException(String message) {
        super("O produto " + message + " Não possui quantidade suficiente");
    }
}
