package com.api.chamados.controller.suporte;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.suporte.ProfissionalService;
import com.api.chamados.service.suporte.dto.ProfissionalDto;
import com.api.chamados.service.suporte.form.ProfissionalFiltroForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @GetMapping()
    @Operation(summary = "Listar profissionais",
            description = "Retorna uma lista de profissionais. " +
                    "É possível aplicar filtros com base nos campos disponíveis no formulário de filtro."
    )
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<ResponseDto<Page<ProfissionalDto>>> listarProfissionais(@ParameterObject ProfissionalFiltroForm filtro,
                                                                 @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable){
       var profissionais = profissionalService.listarProfissionais(pageable, filtro);
        return  ResponseDto.<Page<ProfissionalDto>>builder()
                .response(profissionais)
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("{proNrId}")
    @Operation(summary = "Buscar profissional por ID", description = "Retorna os dados de um profissional a partir do seu ID.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<ResponseDto<ProfissionalDto>> buscarPorId(@PathVariable long proNrId){
        var profissional = profissionalService.buscarProfissionalPorId(proNrId);
        return  ResponseDto.<ProfissionalDto>builder()
                .status(HttpStatus.CREATED)
                .response(profissional)
                .build();
    }

    @PatchMapping("{proNrId}")
    @Operation(summary = "Ativar ou inativar profissional", description = "Altera o status de um profissional entre ativo e inativo com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<ResponseDto<Void>> ativarOuInativar(@PathVariable long proNrId){
        profissionalService.ativarDesativarProfissional(proNrId);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }
}
