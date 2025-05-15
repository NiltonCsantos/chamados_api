package com.api.chamados.service.atendimento.form;

import com.api.chamados.model.atendimento.enums.StatusChamadoEnum;

public record ChamadoFiltroForm(
        StatusChamadoEnum chaTxStatus,
        String chaTxTitulo,
        Long eqiNrId
) {
}
