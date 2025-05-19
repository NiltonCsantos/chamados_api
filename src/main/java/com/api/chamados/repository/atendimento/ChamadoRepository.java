package com.api.chamados.repository.atendimento;

import com.api.chamados.model.atendimento.ChamadoEntidade;
import com.api.chamados.service.atendimento.dto.ChamdoComHistoricoDto;
import com.api.chamados.service.atendimento.dto.QuantidadeChamadosDto;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoEntidade, Long> {
    @Query(nativeQuery = true,
            value = """
                    select
                    	cha.*
                    from atendimento.cha_chamado cha
                    where (:#{#filtro.chaTxTitulo()==null} or upper(cha.cha_tx_titulo) like upper(concat(coalesce(:#{#filtro.chaTxTitulo()}, ''), '%')))
                          and (:#{#filtro.chaTxStatus() == null } or cha.cha_tx_titulo = :#{#filtro?.chaTxStatus()?.name()})
                          and (:#{#filtro.eqiNrId() == null } or cha.eqi_nr_id = :#{#filtro?.eqiNrId()})    
                    """)
    Page<ChamadoEntidade> listarChamadosByFiltro(ChamadoFiltroForm filtro, Pageable pageable);

    @Query(nativeQuery = true,
            value = """
                    select
                    	cha.cha_tx_titulo,
                    	cha.cha_tx_descricao,
                    	usu_empresa.usu_tx_nome,
                    	( select
                    			jsonb_agg(
                    				json_build_object(
                    					'hicNrId', hic.hic_nr_id,
                    					'hicTxJustificativa', hic.hic_tx_justificativa,
                    					'hicDtAtualizacao', hic.hic_dt_atualizacao,
                    					'hicTxStatus', hic.hic_tx_status
                    				)
                    			)
                    	  from atendimento.hic_historico_chamado hic
                    	  inner join suporte.pro_profissional pro on pro.pro_nr_id = hic.pro_nr_id
                    	  inner join autenticacao.usu_usuario usu on usu.usu_nr_id = pro.pro_nr_id
                    	  where hic.cha_nr_id = cha.cha_nr_id
                    	)
                    from atendimento.cha_chamado cha
                    inner join atendimento.emp_empresa emp on emp.emp_nr_id = cha.emp_nr_id
                    inner join autenticacao.usu_usuario usu_empresa on usu_empresa.usu_nr_id = emp.emp_nr_id
                    where cha.cha_nr_id =:chaNrId
                    """)
    Optional<ChamdoComHistoricoDto> findChamadoComHistoricoById(Long chaNrId);

    @Query(nativeQuery = true,
            value = """
                    select
                    	count(cha.cha_nr_id) TotalPedidos,
                    	count(case when cha.cha_tx_ultimo_status = 'CONCLUIDO' then 1 end) as totalFinalizados,
                    	count(case when cha.cha_tx_ultimo_status = 'ABERTO' then 1 end) as totalAberto,
                    	count(case when cha.cha_tx_ultimo_status = 'EM_ANDAMENTO' then 1 end) as totalEmAndamento,
                    	count(case when cha.cha_tx_ultimo_status = 'CANCELADO' then 1 end) as totalCancelado
                    from atendimento.cha_chamado cha
                    inner join atendimento.emp_empresa emp on emp.emp_nr_id = cha.emp_nr_id
                    where :munNrId is null or emp.mun_nr_id =:munNrId
                    """
    )
    QuantidadeChamadosDto countChamadosPorStatus(Long munNrId);
}
