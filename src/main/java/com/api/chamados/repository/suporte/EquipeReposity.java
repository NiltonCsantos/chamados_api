package com.api.chamados.repository.suporte;

import com.api.chamados.model.suporte.EquipeEntidade;
import com.api.chamados.model.suporte.enums.EquipeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeReposity extends JpaRepository<EquipeEntidade,Long> {
    boolean existsByEqiTxNome(EquipeEnum eqiTxNome);
}
