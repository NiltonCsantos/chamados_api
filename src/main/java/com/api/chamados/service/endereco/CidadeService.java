package com.api.chamados.service.endereco;

import com.api.chamados.service.endereco.dto.CidadeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CidadeService {
    Page<CidadeDto> findAllCIdades(Pageable pageable);
}
