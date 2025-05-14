package com.api.chamados.model.atendimento.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusChamadoEnum {
    ABERTO("ABERTO"),
    ALTERADO("ALTERADO"),

    EM_ANDAMENTO("EM_ANDAMENTO"),

    CANCELADO("CANCELADO"),

    CONCLUIDO("CONCLUIDO");

    private String chamado;
}
