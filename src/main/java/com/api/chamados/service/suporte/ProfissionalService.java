package com.api.chamados.service.suporte;

import com.api.chamados.service.suporte.dto.ProfissionalDto;
import com.api.chamados.service.suporte.form.ProfissionalFiltroForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfissionalService {
    ProfissionalDto buscarProfissionalPorId(Long proNrId);
    Page<ProfissionalDto> listarProfissionais(Pageable pageable, ProfissionalFiltroForm filtro);
    void ativarDesativarProfissional(Long proNrId);
}
