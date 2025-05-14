package com.api.chamados.service.autenticacao.usuario.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeiranteRegistroForm(
        @NotBlank(message = "O campo nome deve ser preenchido")
        String usuTxNome,
        @NotBlank(message = "O campo email deve ser preenchido")
        String usuTxEmail,
        @NotBlank(message = "O campo senha deve ser preenchido")
        String usuTxSenha,
        String ivTxImagem,
        @NotBlank(message = "O número da loja é obrigatório")
        String venTxNumeroLoja,
        @NotNull(message = "o nome do nicho é obrigatório")
        Long nicNrId
)  {

}
