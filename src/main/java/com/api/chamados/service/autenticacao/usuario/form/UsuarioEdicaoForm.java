package com.api.chamados.service.autenticacao.usuario.form;

import jakarta.validation.constraints.NotBlank;

public record UsuarioEdicaoForm(
        @NotBlank(message = "o campo nome é obrigatório")
        String usuTxNome
) {
}
