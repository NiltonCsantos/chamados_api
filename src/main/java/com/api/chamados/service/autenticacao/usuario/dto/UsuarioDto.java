package com.api.chamados.service.autenticacao.usuario.dto;

import com.api.chamados.model.autenticacao.UsuarioEntidade;

public record UsuarioDto(
        Long usuNrId,
        String usuTxNome,
        String usuTxEmail
) {
    public UsuarioDto(UsuarioEntidade entidade) {
        this(entidade.getId(),
                entidade.getUsuTxNome(),
                entidade.getUsuTxEmail());
    }
}
