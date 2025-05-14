package com.api.chamados.service.atendimento.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChamadoForm(
        @NotBlank(message = "É necessário informar a descricao do chamado")
        String chaTxDescricao,
        @NotNull(message = "É necessário informar a equipe")
        Long eqiNrId,
        @NotBlank(message = "É necessário informar o titulo do chamado")
        String chaTxTitulo
) {
}
