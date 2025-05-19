package com.api.chamados.service.atendimento.dto;

public interface QuantidadeChamadosDto {
    Long getTotalPedidos();
    Long getTotalFinalizados();
    Long getTotalAberto();
    Long getTotalEmAndamento();
    Long getTotalCancelado();
}
