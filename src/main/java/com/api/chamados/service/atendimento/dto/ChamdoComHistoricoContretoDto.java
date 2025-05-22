package com.api.chamados.service.atendimento.dto;

import com.api.chamados.config.ListaDadosSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChamdoComHistoricoContretoDto{
    private String chaTxTitulo;
    private String chaTxDescricao;
    private String usuTxNome;
    private String chaTxImage;
    @JsonSerialize(using = ListaDadosSerializer.class)
    private List<String> historicos;
}
