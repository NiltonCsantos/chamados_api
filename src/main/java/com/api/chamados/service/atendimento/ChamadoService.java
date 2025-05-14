package com.api.chamados.service.atendimento;

import com.api.chamados.service.atendimento.dto.ChamadoDto;
import com.api.chamados.service.atendimento.dto.ChamadoDtoComHistorico;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import com.api.chamados.service.atendimento.form.ChamadoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChamadoService {
    Page<ChamadoDtoComHistorico> listarChamadosComFiltros(ChamadoFiltroForm filtro, Pageable pageable);
    ChamadoDto buscarChamadoPorId(Long chaNrId);
    void gerenciarChamado(ChamadoForm form, Long chaNrId);
}
