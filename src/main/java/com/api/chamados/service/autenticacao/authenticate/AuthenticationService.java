package com.api.chamados.service.autenticacao.authenticate;

import com.api.chamados.model.autenticacao.enums.PerfilEnum;
import com.api.chamados.service.autenticacao.authenticate.dto.AuthDto;
import com.api.chamados.service.autenticacao.usuario.form.UsuarioLoginForm;
import com.api.chamados.service.autenticacao.usuario.form.UsuarioRegistroForm;

public interface AuthenticationService {
    void salvarEmpresa(UsuarioRegistroForm user);
    void salvarUsuario(UsuarioRegistroForm user);
    AuthDto fazerLogin(UsuarioLoginForm user);
    AuthDto fazerLoginComToken(String refreshToken);
    String ativarConta(String token);
}
