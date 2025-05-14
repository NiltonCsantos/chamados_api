package com.api.chamados.exceptions;

@Deprecated(forRemoval = true)
public class TokenException extends RuntimeException {
    public TokenException() {
        super("Token Expirado");
    }
}
