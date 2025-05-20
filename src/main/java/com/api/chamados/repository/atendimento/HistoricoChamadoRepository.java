package com.api.chamados.repository.atendimento;

import com.api.chamados.model.atendimento.HistoricoChamadoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistoricoChamadoRepository extends JpaRepository<HistoricoChamadoEntidade, Long> {

}
