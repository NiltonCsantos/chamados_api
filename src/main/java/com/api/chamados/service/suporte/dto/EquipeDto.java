package com.api.chamados.service.suporte.dto;

import com.api.chamados.model.suporte.EquipeEntidade;
import com.api.chamados.model.suporte.enums.EquipeEnum;

public record EquipeDto(
        EquipeEnum eqiTxNome,
        Long eqiNrId
) {
    public static EquipeDto of(EquipeEntidade entidade) {
        if (entidade == null)
            return null;

        return new EquipeDto(
                entidade.getEqiTxNome(),
                entidade.getEqiNrId()
        );
    }
}
