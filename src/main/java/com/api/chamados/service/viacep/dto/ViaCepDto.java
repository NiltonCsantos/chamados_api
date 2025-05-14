package com.api.chamados.service.viacep.dto;

public record ViaCepDto(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        Integer ibge,
        String ddd
) {

}
