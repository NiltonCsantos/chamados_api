package com.api.chamados.service.atendimento;

import com.api.chamados.service.atendimento.dto.*;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import com.api.chamados.service.atendimento.form.ChamadoForm;
import com.api.chamados.service.atendimento.form.TransferenciaChamadoProfissionalForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ChamadoService {
    Page<ChamadoDto> listarChamadosComFiltros(ChamadoFiltroForm filtro, Pageable pageable);
    ChamadoDto buscarChamadoPorId(Long chaNrId);
    void gerenciarChamado(ChamadoForm form, MultipartFile imagem, Long chaNrId);
    ChamdoComHistoricoContretoDto buscarChamadoComHistoricoPorId(Long chaNrId);
    QuantidadeChamadosDto quantidadeChamadosPorStatus(Long munNrId);
    QuantidadeChamadoPorEquipe QuantidadeChamadoPorEquipe(Long munNrId);
    Page<QuantidadeChamadoMensalDto> getChamadosPorMes(Long munNrId, Pageable pageable);
    void transferirChamado(TransferenciaChamadoProfissionalForm form);
}
