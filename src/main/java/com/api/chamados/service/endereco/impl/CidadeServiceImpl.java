package com.api.chamados.service.endereco.impl;

import com.api.chamados.repository.endereco.MunicipioRepository;
import com.api.chamados.service.endereco.CidadeService;
import com.api.chamados.service.endereco.dto.CidadeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CidadeServiceImpl implements CidadeService {
    private final MunicipioRepository municipioRepository;
    @Override
    public Page<CidadeDto> findAllCIdades(Pageable pageable) {
        return municipioRepository.findAll(pageable).map(CidadeDto::of);
    }
}
