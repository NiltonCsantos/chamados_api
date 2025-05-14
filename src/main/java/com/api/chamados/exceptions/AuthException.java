package com.api.chamados.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class AuthException extends AuthenticationException {
    public AuthException(String msg, Throwable cause) {super(msg, cause);}
}
