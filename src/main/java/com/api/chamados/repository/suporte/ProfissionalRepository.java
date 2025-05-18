package com.api.chamados.repository.suporte;

import com.api.chamados.model.suporte.ProfissionalEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfissionalRepository extends JpaRepository<ProfissionalEntidade, Long> {

    @Query(nativeQuery = true,
            value = """
                    select
                    	count(pro_nr_id) > 0
                    from suporte.pro_profissional pro
                    where pro.pro_tx_cpf =:proTxCpf or pro.pro_tx_celular=:proTxCelular
                    and (:proNrId is null or pro.pro_nr_id != :proNrId)
                    """)
    boolean existsByProTxCpfOrProTxCelular(String proTxCpf, String proTxCelular, Long proNrId);

}
