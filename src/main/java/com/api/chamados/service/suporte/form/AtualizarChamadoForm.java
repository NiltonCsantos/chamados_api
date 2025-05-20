package com.api.chamados.service.suporte.form;

import com.api.chamados.model.atendimento.enums.StatusChamadoEnum;
import com.api.chamados.model.autenticacao.enums.PerfilEnum;
import jakarta.validation.constraints.NotNull;

public record AtualizarChamadoForm(
        @NotNull(message = "É necessário informar o chamado")
        Long chaNrId,
        @NotNull(message = "É necessário informar o status do chamado")
        StatusChamadoEnum chaTxStatus,
        String hicTxJustificativa
) {
}
