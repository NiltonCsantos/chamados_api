package com.api.chamados.service.suporte;

import com.api.chamados.service.suporte.dto.EquipeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipeService {
    Page<EquipeDto> listarEquipe(Pageable pageable, String eqiTxNome);
}
