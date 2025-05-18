package com.api.chamados.controller.autenticacao;

import com.api.chamados.config.handler.ResponseDto;
import com.api.chamados.service.autenticacao.authenticate.AuthenticationService;
import com.api.chamados.service.autenticacao.authenticate.dto.AuthDto;
import com.api.chamados.service.autenticacao.authenticate.form.EmpresaGroup;
import com.api.chamados.service.autenticacao.authenticate.form.ProfissionalGroup;
import com.api.chamados.service.autenticacao.authenticate.form.UsuarioLoginForm;
import com.api.chamados.service.autenticacao.authenticate.form.UsuarioRegistroForm;
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
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("registrar-empresa")
    @Operation(summary = "Cadastro de empresa", description = "Endpoint responsável por cadastrar uma empresa.")
    @ApiResponse(responseCode = "201", description = "CREATED")
    public ResponseEntity<ResponseDto<Void>> registrarEmpresa(@RequestBody @Validated({EmpresaGroup.class, Default.class}) UsuarioRegistroForm form){
        authenticationService.salvarEmpresa(form, null);
        return  ResponseDto.<Void>builder()
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/ativar-conta/{token}")
    @Operation(summary = "Ativar conta", description = "Endpoint responsável por ativar conta do usuário")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<String> registrarUsuario(@PathVariable String token){

        return ResponseEntity.status(HttpStatus.OK).body( authenticationService.ativarConta(token));
    }

    @PatchMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuarios", description = "Endpoint responsável por permitir que um usuario faça login.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<AuthDto> login(@RequestBody @Valid UsuarioLoginForm userLoginForm){
        return  ResponseEntity.ok(authenticationService.fazerLogin(userLoginForm));
    }

    @PostMapping("/login-com-token/{refreshToken}")
    @Operation(summary = "Login de usuarios com token", description = "Endpoint responsável por permitir que um usuario faça login com token.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<AuthDto> loginComToken(@PathVariable String refreshToken){
        return ResponseEntity.ok( this.authenticationService.fazerLoginComToken(refreshToken));
    }
}
