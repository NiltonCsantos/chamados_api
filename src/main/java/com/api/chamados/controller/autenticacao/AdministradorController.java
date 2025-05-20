package com.api.chamados.controller.autenticacao;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.autenticacao.authenticate.AuthenticationService;
import com.api.chamados.service.autenticacao.authenticate.form.ProfissionalGroup;
import com.api.chamados.service.autenticacao.authenticate.form.UsuarioRegistroForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/admin")
@RequiredArgsConstructor
public class AdministradorController {

    private final AuthenticationService authenticationService;

    @PostMapping("registrar-profissional")
    @Operation(summary = "Registrar profissional", description = "Endpoint responsável por cadastrar um novo profissional no sistema.")
    @ApiResponse(responseCode = "201", description = "CREATED")
    public ResponseEntity<ResponseDto<Void>> registrarProfissional(@RequestBody @Validated({ProfissionalGroup.class, Default.class}) UsuarioRegistroForm form){
        authenticationService.salvarProfissional(form,null);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("atualizar-profissional/{proNrId}")
    @Operation(summary = "Atualizar profissional", description = "Endpoint responsável por atualizar os dados de um profissional existente.")
    @ApiResponse(responseCode = "204", description = "NO-CONTENT")
    public ResponseEntity<ResponseDto<Void>> registrarProfissional(@RequestBody @Validated({ProfissionalGroup.class, Default.class}) UsuarioRegistroForm form,
                                                                   @PathVariable long proNrId){
        authenticationService.salvarProfissional(form,proNrId);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }
}
