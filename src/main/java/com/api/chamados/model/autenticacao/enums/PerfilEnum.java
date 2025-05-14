package com.api.chamados.model.autenticacao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PerfilEnum {
    ADMINISTRADOR("ADMINISTRADOR"),
    SUPORTE("SUPORTE"),
    AUXILIAR_ADMINISTRATIVO("AUXILIAR_ADMINISTRATIVO"),
    EMPRESA("EMPRESA");

    private final String perfil;
}
