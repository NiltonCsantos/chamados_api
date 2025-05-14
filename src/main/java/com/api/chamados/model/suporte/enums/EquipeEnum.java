package com.api.chamados.model.suporte.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EquipeEnum {
    TECNICO("ADMINISTRADOR"),
    SUPORTE("SUPORTE");

    private String equiTxNome;
}
