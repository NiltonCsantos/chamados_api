package com.api.chamados.repository.autenticacao;

import com.api.chamados.model.autenticacao.PerfilEntidade;
import com.api.chamados.model.autenticacao.enums.PerfilEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilEntidade, Long> {

    @Query(nativeQuery = true,
            value = """
                    select per.* from autenticacao.per_perfil per
                    where per.per_tx_nome =:#{#perTxNome.name()}
                    """)
    Optional<PerfilEntidade> findByPerfilUsuario(PerfilEnum perTxNome);

    @Query(nativeQuery = true,
            value = """
                    select per.* from autenticacao.per_perfil per
                    where per.per_tx_nome = 'VENDEDOR'
                    """)
    Optional<PerfilEntidade> findByPerfilVendedor();

    boolean existsByPertxNome(PerfilEnum pertxNome);
}
