package com.api.chamados.service.viacep.impl;

import com.api.chamados.model.endereco.EstadoEntidade;
import com.api.chamados.model.endereco.MunicipioEntidade;
import com.api.chamados.repository.endereco.EstadoRepository;
import com.api.chamados.repository.endereco.MunicipioRepository;
import com.api.chamados.feign.ViaCepFeign;
import com.api.chamados.service.viacep.EstadosMapper;
import com.api.chamados.service.viacep.ViaCepService;
import com.api.chamados.service.viacep.dto.ConsultaCepDto;
import com.api.chamados.service.viacep.dto.ViaCepDto;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepServiceImpl implements ViaCepService {

    private final ViaCepFeign viaCepFeign;
    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;
    private final EstadosMapper estadosMapper;


    @Override
    @Transactional
    public ConsultaCepDto buscarCepEcadastrarEnderecoNaBase(String cep) {
        try {
            ViaCepDto viaCepDto = viaCepFeign.buscaEnderecoCep(cep);
            if (viaCepDto.uf() == null) {
                throw new RuntimeException("Cep não encontrado");
            }

            final Long munNrId;
            final Long estNrId;

            var municipioOptional = municipioRepository.findByMunTxCodigoIbge(viaCepDto.ibge().toString());
            if (municipioOptional.isEmpty()) {

                var estado = estadoRepository.findByEstTxSigla(viaCepDto.uf().toUpperCase())
                        .orElseGet(() -> EstadoEntidade
                                .builder()
                                .estTxNome(estadosMapper.getEstado().get(viaCepDto.uf()).toUpperCase())
                                .estTxSigla(viaCepDto.uf().toUpperCase())
                                .build());

//                String cidadeFormatada = Regex.normalizar(viaCepDto.localidade());

                if (estado.getEstNrId() == null) {
                    estadoRepository.save(estado);
                }

                var municipioEntidade = MunicipioEntidade
                        .builder()
                        .estNrId(estado.getEstNrId())
                        .munTxNome(viaCepDto.localidade())
                        .munTxCodigoIbge(viaCepDto.ibge().toString())
                        .build();

                municipioRepository.save(municipioEntidade);
                munNrId = municipioEntidade.getMunNrId();
                estNrId = municipioEntidade.getEstNrId();
            } else {
                var municipioEntidade = municipioOptional.get();
                munNrId = municipioEntidade.getMunNrId();
                estNrId = municipioEntidade.getEstNrId();
            }

            return ConsultaCepDto.of(viaCepDto, munNrId, estNrId);
        } catch (FeignException.FeignServerException | FeignException.FeignClientException e) {
            throw new RuntimeException("Cep inválido");
        }
    }
}
