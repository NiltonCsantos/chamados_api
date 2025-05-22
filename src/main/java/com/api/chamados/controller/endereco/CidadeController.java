package com.api.chamados.controller.endereco;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.atendimento.dto.ChamadoDto;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import com.api.chamados.service.endereco.CidadeService;
import com.api.chamados.service.endereco.dto.CidadeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;

    @Operation(summary = "Listar cidade", description = "Serviços responsável por listar cidades.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ChamadoDto.class)))
    @GetMapping()
    public ResponseEntity<ResponseDto<Page<CidadeDto>>> listarCidades(
            @ParameterObject ChamadoFiltroForm filtro,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {

        var cidades = cidadeService.findAllCIdades( pageable);

        return ResponseDto.<Page<CidadeDto>>builder()
                .status(HttpStatus.OK)
                .response(cidades)
                .build();
    }
}
