package com.api.chamados.controller.atendimento;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.atendimento.ChamadoService;
import com.api.chamados.service.atendimento.dto.ChamadoDto;
import com.api.chamados.service.atendimento.dto.ChamdoComHistoricoDto;
import com.api.chamados.service.atendimento.dto.QuantidadeChamadosDto;
import com.api.chamados.service.atendimento.form.ChamadoFiltroForm;
import com.api.chamados.service.atendimento.form.ChamadoForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping()
    @Operation(summary = "Cadastro de chamado", description = "Endpoint responsável por cadastrar um chamado.")
    @ApiResponse(responseCode = "201", description = "CREATED")
    public ResponseEntity<ResponseDto<Void>> registrarChamado(@RequestBody @Valid ChamadoForm form){
        chamadoService.gerenciarChamado(form, null);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("{chaNrId}")
    @Operation(summary = "Atualização de chamado", description = "Endpoint responsável por atualizar um chamado.")
    @ApiResponse(responseCode = "204", description = "NO-CONTENT")
    public ResponseEntity<ResponseDto<Void>> atualizarChamado(@PathVariable long chaNrId,
                                                              @RequestBody @Valid ChamadoForm form){
        chamadoService.gerenciarChamado(form, chaNrId);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Listar chamados", description = "Serviços responsável por listar chamados.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ChamadoDto.class)))
    @GetMapping()
    public ResponseEntity<ResponseDto<Page<ChamadoDto>>> listarChamado(
            @ParameterObject ChamadoFiltroForm filtro,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {

        var chamados = chamadoService.listarChamadosComFiltros(filtro, pageable);

        return ResponseDto.<Page<ChamadoDto>>builder()
                .status(HttpStatus.OK)
                .response(chamados)
                .build();
    }



    @GetMapping({"{chaNrId}"})
    public ResponseEntity<ResponseDto<ChamadoDto>> buscarChamadoPorId(
            @PathVariable long chaNrId) {

        var chamado = chamadoService.buscarChamadoPorId(chaNrId);

        return ResponseDto.<ChamadoDto>builder()
                .status(HttpStatus.OK)
                .response(chamado)
                .build();
    }

    @GetMapping("/historico/{chaNrId}")
    public ResponseEntity<ResponseDto<ChamdoComHistoricoDto>> buscarChamadoComHistoricoPorId(
            @PathVariable long chaNrId) {
        var chamado = chamadoService.buscarChamadoComHistoricoPorId(chaNrId);

        return ResponseDto.<ChamdoComHistoricoDto>builder()
                .status(HttpStatus.OK)
                .response(chamado)
                .build();
    }

    @GetMapping("/contar")
    public ResponseEntity<ResponseDto<QuantidadeChamadosDto>> contarChamados(
            @RequestParam(required = false) Long munNrId) {
        var quantidadeChamadp = chamadoService.quantidadeChamadosPorStatus(munNrId);

        return ResponseDto.<QuantidadeChamadosDto>builder()
                .status(HttpStatus.OK)
                .response(quantidadeChamadp)
                .build();
    }
}
