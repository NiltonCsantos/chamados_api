package com.api.chamados.service.atendimento.imp;

import com.api.chamados.config.exceptions.BadRequestException;
import com.api.chamados.config.exceptions.NotFoundException;
import com.api.chamados.model.atendimento.ChamadoEntidade;
import com.api.chamados.model.atendimento.HistoricoChamadoEntidade;
import com.api.chamados.model.atendimento.enums.StatusChamadoEnum;
import com.api.chamados.repository.atendimento.ChamadoRepository;
import com.api.chamados.repository.atendimento.HistoricoChamadoRepository;
import com.api.chamados.repository.suporte.EquipeReposity;
import com.api.chamados.repository.suporte.ProfissionalRepository;
import com.api.chamados.service.atendimento.ChamadoService;
import com.api.chamados.service.atendimento.dto.*;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import com.api.chamados.service.atendimento.form.ChamadoForm;
import com.api.chamados.service.atendimento.form.TransferenciaChamadoProfissionalForm;
import com.api.chamados.service.imagem.ImagemService;
import com.api.chamados.service.base.impl.BaseServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChamadoServiceImpl extends BaseServiceImpl implements ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final HistoricoChamadoRepository historicoChamadoRepository;
    private final EquipeReposity equipeReposity;
    private final ImagemService imagemService;
    private final ProfissionalRepository profissionalRepository;

    @Override
    public Page<ChamadoDto> listarChamadosComFiltros(ChamadoFiltroForm filtro, Pageable pageable) {
        return chamadoRepository.listarChamadosByFiltro(filtro, pageable).map(entidade -> ChamadoDto.of(entidade, null));
    }

    @Override
    public ChamadoDto buscarChamadoPorId(Long chaNrId) {
        var chamado = chamadoRepository.findById(chaNrId)
                .orElseThrow(() -> new NotFoundException("Não foi possível localizar chamdo"));
        return ChamadoDto.of(chamado, imagemService.getFileAsBase64(chamado.getChaTxImage()));
    }

    @Override
    @Transactional
    public void gerenciarChamado(ChamadoForm form, MultipartFile imagem, Long chaNrId) {

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

        if (imagem != null && !imagem.isEmpty()) {
            var path = imagemService.upload(imagem);
            chamado.setChaTxImage(path);
        }

        chamadoRepository.save(chamado);
    }

    @Override
    public ChamdoComHistoricoContretoDto buscarChamadoComHistoricoPorId(Long chaNrId) {
        var chamado = chamadoRepository.findChamadoComHistoricoById(chaNrId)
                .orElseThrow(() -> new NotFoundException("Não foi possível localizar chamado"));

        ChamdoComHistoricoContretoDto response = new ChamdoComHistoricoContretoDto();
        response.setChaTxTitulo(chamado.getChaTxTitulo());
        response.setChaTxDescricao(chamado.getChaTxDescricao());
        response.setUsuTxNome(chamado.getUsuTxNome());
        response.setHistoricos(chamado.getHistoricos());
        response.setChaTxImage(chamado.getChaTxImage()!= null ? imagemService.getFileAsBase64(chamado.getChaTxImage()) : null);

        return response;
    }

    @Override
    public QuantidadeChamadosDto quantidadeChamadosPorStatus(Long munNrId) {
        return chamadoRepository.countChamadosPorStatus(munNrId);
    }

    @Override
    public QuantidadeChamadoPorEquipe QuantidadeChamadoPorEquipe(Long munNrId) {
        return chamadoRepository.findChamadosPorEquipe(munNrId);
    }

    @Override
    public Page<QuantidadeChamadoMensalDto> getChamadosPorMes(Long munNrId, Pageable pageable) {
        return chamadoRepository.findChamadosPorMes(pageable,munNrId);
    }

    @Override
    public void transferirChamado(TransferenciaChamadoProfissionalForm form) {
       if (!profissionalRepository.existsById(form.proNrIdAtual())){
           throw new NotFoundException("Não foi possível localizar o profissional atual");
       }

        if (!profissionalRepository.existsById(form.proNrIdNovo())){
            throw new NotFoundException("Não foi possível localizar o novo profissional");
        }

        var chamado = chamadoRepository.findById(form.chaNrId())
                .orElseThrow(  () -> new NotFoundException("Não foi possível localizar chamado"));

        if (chamado.getChaTxUltimoStatus() == StatusChamadoEnum.CONCLUIDO || chamado.getChaTxUltimoStatus() == StatusChamadoEnum.CANCELADO)
            throw new BadRequestException("Não foi possível transferir chamado pois ele está com status: " + chamado.getChaTxUltimoStatus());

        var historico = HistoricoChamadoEntidade
                .builder()
                .hicTxStatus(StatusChamadoEnum.EM_ANDAMENTO)
                .hicDtAtualizacao(LocalDateTime.now())
                .chaNrId(form.chaNrId())
                .proNrId(form.proNrIdNovo())
                .build();

        historicoChamadoRepository.save(historico);
    }
}
