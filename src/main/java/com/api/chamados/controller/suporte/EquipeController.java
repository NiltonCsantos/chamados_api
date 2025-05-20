package com.api.chamados.controller.suporte;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.suporte.dto.EquipeDto;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import com.api.chamados.service.suporte.EquipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/equipes")
@RequiredArgsConstructor
public class EquipeController {

    private final EquipeService equipeService;

    @GetMapping()
    @Operation(summary = "Listar equipes", description = "Retorna uma lista de equipes cadastradas no sistema. "
                    + "É possível filtrar pelo nome da equipe (eqiTxNome).")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<ResponseDto<Page<EquipeDto>>> listarEquipe(
            @RequestParam(required = false) String eqiTxNome,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {

        var equipes = equipeService.listarEquipe(pageable, eqiTxNome);

        return ResponseDto.<Page<EquipeDto>>builder()
                .status(HttpStatus.OK)
                .response(equipes)
                .build();
    }
}
