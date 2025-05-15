package com.api.chamados.repository.atendimento;

import com.api.chamados.model.atendimento.ChamadoEntidade;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoEntidade, Long> {
    @Query(nativeQuery = true,
            value = """
                    select
                    	cha.*
                    from atendimento.cha_chamado cha
                    where                        
                         (:#{#filtro.chaTxTitulo()==null} or cha.cha_tx_ultimo_status like concat(coalesce(:#{#filtro.chaTxTitulo()?.name()}, ''), '%'))
                          and (:#{#filtro.chaTxStatus() == null } or cha.cha_tx_titulo = :#{#filtro?.chaTxStatus()?.name()})
                          and (:#{#filtro.eqiNrId() == null } or cha.eqi_nr_id = :#{#filtro?.eqiNrId()})    
                    """)
    Page<ChamadoEntidade> listarChamadosByFiltro(ChamadoFiltroForm filtro, Pageable pageable);
}
