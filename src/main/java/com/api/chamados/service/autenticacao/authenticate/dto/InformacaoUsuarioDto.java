package com.api.chamados.service.autenticacao.authenticate.dto;

import com.api.chamados.model.autenticacao.UsuarioEntidade;
import org.springframework.security.core.GrantedAuthority;

public record InformacaoUsuarioDto(
        Long usuNrId,
        String usuTxNome,
        String usuTxEmail,
        String usuTxAutoridade
) {
    public static InformacaoUsuarioDto of(UsuarioEntidade usuario) {
        if (usuario == null)
            return null;

        String autoridade = usuario.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        return new InformacaoUsuarioDto(
                usuario.getUsuNrId(),
                usuario.getUsuTxNome(),
                usuario.getUsuTxEmail(),
                autoridade
        );
    }
}
