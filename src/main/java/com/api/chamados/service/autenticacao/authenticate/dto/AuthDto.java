package com.api.chamados.service.autenticacao.authenticate.dto;

public record AuthDto(
        String acessToken,
        String refreshToken
) {
}
