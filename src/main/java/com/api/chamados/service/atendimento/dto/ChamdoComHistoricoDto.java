package com.api.chamados.service.atendimento.dto;

import java.util.List;

public interface ChamdoComHistoricoDto {
    String getChaTxTitulo();
    String getChaTxDescricao();
    String getUsuTxNome();
    List<String> getHistoricos();
}
