package com.api.chamados.service.suporte.impl;


import com.api.chamados.config.exceptions.BadRequestException;
import com.api.chamados.config.exceptions.NotFoundException;
import com.api.chamados.repository.autenticacao.UsuarioRepository;
import com.api.chamados.repository.suporte.ProfissionalRepository;
import com.api.chamados.service.suporte.ProfissionalService;
import com.api.chamados.service.suporte.dto.ProfissionalDto;
import com.api.chamados.service.suporte.form.ProfissionalFiltroForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ProfissionalDto buscarProfissionalPorId(Long proNrId) {
        return profissionalRepository.findProfissionalById(proNrId)
                .orElseThrow(() -> new NotFoundException("Não foi possível localizar profisisonal"));
    }

    @Override
    public Page<ProfissionalDto> listarProfissionais(Pageable pageable, ProfissionalFiltroForm filtro) {
        return profissionalRepository.findAllByByFiltros(filtro, pageable);
    }

    @Override
    public void ativarDesativarProfissional(Long proNrId) {
        if (profissionalRepository.existsByChaAtivo(proNrId))
            throw new BadRequestException("Não foi possível desativar o profisisonal, pois ele possui um ou mais chamados ativos");

        var usuarioEntidade = usuarioRepository
                .findById(proNrId)
                .orElseThrow(() -> new NotFoundException("Não foi possível localizar profisisonal"));

        usuarioEntidade.setUsublAtivo(!usuarioEntidade.isUsublAtivo());
        usuarioRepository.save(usuarioEntidade);
    }
}
