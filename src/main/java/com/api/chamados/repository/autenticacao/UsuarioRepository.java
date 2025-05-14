package com.api.chamados.repository.autenticacao;

import com.api.chamados.model.autenticacao.CustomUserDetails;
import com.api.chamados.model.autenticacao.UsuarioEntidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntidade, Long> {
    CustomUserDetails findByUsuTxEmail(String email);
}



