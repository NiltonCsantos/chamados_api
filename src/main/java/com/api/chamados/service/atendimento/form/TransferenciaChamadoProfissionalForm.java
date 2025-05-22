package com.api.chamados.service.atendimento.form;

import jakarta.validation.constraints.NotNull;

public record TransferenciaChamadoProfissionalForm(
        @NotNull(message = "É necessário informar o profissional atual")
        Long proNrIdAtual,
        @NotNull(message = "É necessário informar o profissinal de origem")
        Long proNrIdNovo,
        @NotNull(message = "É necessário informar o chamado")
        Long chaNrId
) {
}
