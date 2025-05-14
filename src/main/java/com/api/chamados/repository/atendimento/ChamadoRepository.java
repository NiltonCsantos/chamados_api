package com.api.chamados.repository.atendimento;

import com.api.chamados.model.atendimento.ChamadoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoEntidade, Long> {
}
