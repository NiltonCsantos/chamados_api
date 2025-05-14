package com.api.chamados.controller.atendimento;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.atendimento.ChamadoService;
import com.api.chamados.service.atendimento.form.ChamadoForm;
import com.api.chamados.service.autenticacao.usuario.form.EmpresaGroup;
import com.api.chamados.service.autenticacao.usuario.form.UsuarioRegistroForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping("chamados")
    @Operation(summary = "Cadastro de chamado", description = "Endpoint responsável por cadastrar um chamado.")
    @ApiResponse(responseCode = "201", description = "CREATED")
    public ResponseEntity<ResponseDto<Void>> registrarChamado(@RequestBody @Valid ChamadoForm form){
        chamadoService.gerenciarChamado(form, null);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("chamados/{chaNrId}")
    @Operation(summary = "Atualização de chamado", description = "Endpoint responsável por atualizar um chamado.")
    @ApiResponse(responseCode = "204", description = "NO-CONTENT")
    public ResponseEntity<ResponseDto<Void>> atualizarChamado(@PathVariable long chaNrId,
                                                              @RequestBody @Valid ChamadoForm form){
        chamadoService.gerenciarChamado(form, chaNrId);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }
}
