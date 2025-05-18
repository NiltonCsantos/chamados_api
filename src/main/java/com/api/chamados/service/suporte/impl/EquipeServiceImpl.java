package com.api.chamados.service.suporte.impl;

import com.api.chamados.repository.suporte.EquipeReposity;
import com.api.chamados.service.suporte.EquipeService;
import com.api.chamados.service.suporte.dto.EquipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipeServiceImpl implements EquipeService {

    private final EquipeReposity equipeReposity;

    @Override
    public Page<EquipeDto> listarEquipe(Pageable pageable, String eqiTxNome) {
        return equipeReposity.findAll(pageable).map(EquipeDto::of);
    }
}
