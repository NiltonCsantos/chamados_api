package com.api.chamados.service.autenticacao.authenticate;

import com.api.chamados.service.autenticacao.authenticate.dto.AuthDto;
import com.api.chamados.service.autenticacao.authenticate.form.UsuarioLoginForm;
import com.api.chamados.service.autenticacao.authenticate.form.UsuarioRegistroForm;

public interface AuthenticationService {
    void salvarEmpresa(UsuarioRegistroForm form);
    void salvarProfissional(UsuarioRegistroForm form);
    AuthDto fazerLogin(UsuarioLoginForm form);
    AuthDto fazerLoginComToken(String refreshToken);
    String ativarConta(String token);
}
