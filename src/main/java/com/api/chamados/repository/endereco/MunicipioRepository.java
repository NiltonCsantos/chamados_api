package com.api.chamados.repository.endereco;

import com.api.chamados.model.endereco.MunicipioEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioEntidade, Long> {
    Optional<MunicipioEntidade> findByMunTxCodigoIbge(String aLong);
    boolean existsByMunTxCodigoIbge(String munTxCodigoIbge);
}
