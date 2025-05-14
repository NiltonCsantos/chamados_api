package com.api.chamados.repository.endereco;

import com.api.chamados.model.endereco.EstadoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoEntidade, Long> {
    Optional<EstadoEntidade> findByEstTxSigla(String estTxSigla);
}
