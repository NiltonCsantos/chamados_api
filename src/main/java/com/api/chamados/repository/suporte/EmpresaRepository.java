package com.api.chamados.repository.suporte;

import com.api.chamados.model.atendimento.EmpresaEntidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<EmpresaEntidade, Long> {
    boolean existsByEmpTxCnpj(String cnpj);
}
