package com.api.chamados.config;

import com.api.chamados.model.autenticacao.PerfilEntidade;
import com.api.chamados.model.autenticacao.enums.PerfilEnum;
import com.api.chamados.model.suporte.EquipeEntidade;
import com.api.chamados.model.suporte.enums.EquipeEnum;
import com.api.chamados.repository.autenticacao.PerfilRepository;
import com.api.chamados.repository.suporte.EquipeReposity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InicalizadorBanco implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final EquipeReposity equipeReposity;

    @Override
    @Transactional
    public void run(String... args)  {
        List<PerfilEntidade> perfisParaAdicionar = new ArrayList<>();
        for (var perfil: PerfilEnum.values()) {
            if (!perfilRepository.existsByPertxNome(perfil)){
                var perfilEntidade = PerfilEntidade
                        .builder()
                        .pertxNome(perfil)
                        .build();
                perfisParaAdicionar.add(perfilEntidade);
            }
        }
        perfilRepository.saveAll(perfisParaAdicionar);


        List<EquipeEntidade> equipesParaAdicionar = new ArrayList<>();
        for (var equipe: EquipeEnum.values()) {
            if (!equipeReposity.existsByEqiTxNome(equipe)){
                var equipeEntidade = EquipeEntidade
                        .builder()
                        .eqiTxNome(equipe)
                        .build();
                equipesParaAdicionar.add(equipeEntidade);
            }
        }
        equipeReposity.saveAll(equipesParaAdicionar);
    }
}
