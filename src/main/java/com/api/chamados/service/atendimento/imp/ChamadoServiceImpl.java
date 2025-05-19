package com.api.chamados.service.atendimento.imp;

import com.api.chamados.config.exceptions.NotFoundException;
import com.api.chamados.model.atendimento.ChamadoEntidade;
import com.api.chamados.model.atendimento.HistoricoChamadoEntidade;
import com.api.chamados.model.atendimento.enums.StatusChamadoEnum;
import com.api.chamados.repository.atendimento.ChamadoRepository;
import com.api.chamados.repository.atendimento.HistoricoChamadoRepository;
import com.api.chamados.repository.suporte.EquipeReposity;
import com.api.chamados.service.atendimento.ChamadoService;
import com.api.chamados.service.atendimento.dto.ChamadoDto;
import com.api.chamados.service.atendimento.dto.ChamadoDtoComHistorico;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import com.api.chamados.service.atendimento.form.ChamadoForm;
import com.api.chamados.service.base.impl.BaseServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChamadoServiceImpl extends BaseServiceImpl implements ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final HistoricoChamadoRepository historicoChamadoRepository;
    private final EquipeReposity equipeReposity;

    @Override
    public Page<ChamadoDto> listarChamadosComFiltros(ChamadoFiltroForm filtro, Pageable pageable) {
        return chamadoRepository.listarChamadosByFiltro(filtro, pageable).map(ChamadoDto::of);
    }

    @Override
    public ChamadoDto buscarChamadoPorId(Long chaNrId) {
        var chamado = chamadoRepository.findById(chaNrId)
                .orElseThrow(() -> new NotFoundException("Não foi possível localizar chamdo"));
        return ChamadoDto.of(chamado);
    }

    @Override
    @Transactional
    public void gerenciarChamado(ChamadoForm form, Long chaNrId) {

        var emprNrId = this.buscarUsuarioAutenticado().getUsuNrId();

        ChamadoEntidade chamado = chaNrId != null ?
                chamadoRepository.findById(chaNrId)
                        .orElseThrow(() -> new NotFoundException("Chamado não encontrado")) : new ChamadoEntidade();

        if (!equipeReposity.existsById(form.eqiNrId())){
            throw new NotFoundException("Equipe não encontrado");
        }

        chamado.setChaTxUltimoStatus(chamado.getChaNrId() != null ? StatusChamadoEnum.ALTERADO : StatusChamadoEnum.ABERTO);
        chamado.setChaTxDescricao(form.chaTxDescricao());
        chamado.setChaDtAbertura(LocalDateTime.now());
        chamado.setEmpNrId(emprNrId);
        chamado.setEqiNrId(form.eqiNrId());
        chamado.setChaTxTitulo(form.chaTxTitulo());

        var historico = HistoricoChamadoEntidade
                .builder()
                .chaDtAtualizacao(LocalDateTime.now())
                .hicTxStatus(chamado.getChaNrId() != null ? StatusChamadoEnum.ALTERADO : StatusChamadoEnum.ABERTO)
                .usuNrId(emprNrId)
                .build();

        chamadoRepository.save(chamado);
        historico.setChaNrId(chamado.getChaNrId());
        historicoChamadoRepository.save(historico);
    }
}
