package com.api.chamados.service.viacep.dto;

import org.springframework.lang.Nullable;

import java.util.Objects;

public record ConsultaCepDto(
        String cep,
        String localidade,
        String uf,
        Long munNrId,
        Long estNrId
) {
    public static @Nullable ConsultaCepDto of(@Nullable ViaCepDto viaCepDto, Long cidNrId, Long estNrId) {
        if (Objects.isNull(viaCepDto)) {
            return null;
        }
        return new ConsultaCepDto(
                viaCepDto.cep(),
                viaCepDto.localidade(),
                viaCepDto.uf(),
                cidNrId,
                estNrId
        );
    }
}
