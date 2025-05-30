package com.api.chamados.service.atendimento.dto;

import com.api.chamados.model.atendimento.ChamadoEntidade;
import com.api.chamados.model.atendimento.enums.StatusChamadoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ChamadoDto(
        String chaTxDescricao,
        Long eqiNrId,
        String chaTxTitulo,
        StatusChamadoEnum chaTxStatus,
        LocalDateTime chaTxDtAbertura

) {
    public static ChamadoDto of(ChamadoEntidade entidade) {
        if (entidade == null) return null;
        return new ChamadoDto(
                entidade.getChaTxDescricao(),
                entidade.getEqiNrId(),
                entidade.getChaTxTitulo(),
                entidade.getChaTxUltimoStatus(),
                entidade.getChaDtAbertura()
        );
    }
}
