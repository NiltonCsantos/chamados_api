package com.api.chamados.service.autenticacao.authorization;

import com.api.chamados.model.autenticacao.UsuarioEntidade;

public interface AuthorizationService {
    UsuarioEntidade getUser();
}
