package com.api.chamados.repository.suporte;

import com.api.chamados.model.suporte.ProfissionalEntidade;
import com.api.chamados.service.suporte.dto.ProfissionalDto;
import com.api.chamados.service.suporte.form.ProfissionalFiltroForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

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

    @Query(nativeQuery = true,
            value = """
                    select
                    	pro.*,
                    	usu.usu_tx_email,
                    	usu.usu_bl_ativo,
                    	usu.usu_tx_nome,
                    	per.*,
                        eqi.*
                    from suporte.pro_profissional pro
                    inner join autenticacao.usu_usuario usu on usu.usu_nr_id = pro.pro_nr_id
                    inner join autenticacao.per_perfil per on per.per_nr_id  = usu.per_nr_id
                    inner join suporte.eqi_equipe eqi on eqi.eqi_nr_id = pro.eqi_nr_id
                    where (:#{#filtro.eqiNrId() == null} or pro.eqi_nr_id =:#{#filtro.eqiNrId()})
                    and (
                            (:#{#filtro.proTxCpf()==null} or upper(pro.pro_tx_cpf) like upper(concat(coalesce(:#{#filtro.proTxCpf()}, ''), '%')))
                            or (:#{#filtro.proTxNome() == null } or upper(usu.usu_tx_nome) like upper(concat(coalesce(:#{#filtro.proTxNome()}, ''), '%')))
                        )
                    """)
    Page<ProfissionalDto> findAllByByFiltros(ProfissionalFiltroForm filtro, Pageable pageable);

    @Query(nativeQuery = true,
            value = """
                    select
                    	pro.*,
                    	usu.usu_tx_email,
                    	usu.usu_bl_ativo,
                    	usu.usu_tx_nome,
                    	per.*
                    from suporte.pro_profissional pro
                    inner join autenticacao.usu_usuario usu on usu.usu_nr_id = pro.pro_nr_id
                    inner join autenticacao.per_perfil per on per.per_nr_id  = usu.per_nr_id
                    where pro.pro_nr_id =:proNrId
                    """)
    Optional<ProfissionalDto> findProfissionalById(@Param("proNrId") Long proNrId);

    @Query(nativeQuery = true,
            value = """
                    select
                    	count(pro.pro_nr_id)>0
                    from suporte.pro_profissional pro
                    inner join autenticacao.usu_usuario usu on usu.usu_nr_id = pro.pro_nr_id
                    inner join suporte.eqi_equipe eqi on eqi.eqi_nr_id = pro.pro_nr_id
                    inner join atendimento.cha_chamado cha on cha.eqi_nr_id = eqi.eqi_nr_id
                    where (cha.cha_tx_ultimo_status != 'CANCELADO' and cha.cha_tx_ultimo_status != 'CONCLUIDO')
                    and pro.pro_nr_id =:proNrId
                    """)
    boolean existsByChaAtivo(Long proNrId);
}
