package com.api.chamados.controller.endereco;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.atendimento.dto.QuantidadeChamadoMensalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("v1/estados")
public class EstadoController {
//    @GetMapping()
//    public ResponseEntity<ResponseDto<Page<QuantidadeChamadoMensalDto>>> contarChamadosPorMes(
//            @RequestParam(required = false) Long munNrId,
//            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable
//    ) {
//        var quantidadeChamadp = chamadoService.getChamadosPorMes(munNrId,  pageable);
//
//        return ResponseDto.<Page<QuantidadeChamadoMensalDto>>builder()
//                .status(HttpStatus.OK)
//                .response(quantidadeChamadp)
//                .build();
//    }
}
