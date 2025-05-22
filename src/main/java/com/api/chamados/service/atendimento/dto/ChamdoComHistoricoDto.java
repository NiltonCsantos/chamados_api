package com.api.chamados.service.atendimento.dto;

import com.api.chamados.config.ListaDadosSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public interface ChamdoComHistoricoDto {
    String getChaTxTitulo();
    String getChaTxDescricao();
    String getUsuTxNome();

    String getChaTxImage();

    @JsonSerialize(using = ListaDadosSerializer.class)
    List<String> getHistoricos();
}
