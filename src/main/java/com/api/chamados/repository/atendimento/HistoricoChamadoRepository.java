package com.api.chamados.repository.atendimento;

import com.api.chamados.model.atendimento.HistoricoChamadoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoChamadoRepository extends JpaRepository<HistoricoChamadoEntidade, Long> {
}
