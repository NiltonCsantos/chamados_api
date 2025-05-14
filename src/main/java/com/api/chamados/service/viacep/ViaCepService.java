package com.api.chamados.service.viacep;

import com.api.chamados.service.viacep.dto.ConsultaCepDto;

public interface ViaCepService {
    ConsultaCepDto buscarCepEcadastrarEnderecoNaBase(String cep);
}
