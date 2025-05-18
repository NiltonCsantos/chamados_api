package com.api.chamados.service.suporte.dto;

import com.api.chamados.model.autenticacao.enums.PerfilEnum;

public interface ProfissionalDto {
    Long getProNrId();
    Boolean getUsuBlAtivo();
    String getUsuTxNome();
    Long getPerNrId();
    PerfilEnum getPerTxNome();
    String getProTxCpf();
    String getProTxCelular();
    String getUsuTxEmail();
    String getEquiTxNome();
    Long getEqiNrId();
}
