package com.api.chamados.service.endereco.dto;

import com.api.chamados.model.endereco.MunicipioEntidade;

public record CidadeDto(
    Long cidNrId,
    String cidTxNome
) {
    public static CidadeDto of(MunicipioEntidade entidade) {
        if (entidade == null)
            return null;
        return new CidadeDto(entidade.getMunNrId(), entidade.getMunTxNome());
    }
}
