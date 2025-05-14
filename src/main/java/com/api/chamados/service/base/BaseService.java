package com.api.chamados.service.base;

import com.api.chamados.model.autenticacao.UsuarioEntidade;
public interface BaseService {
    UsuarioEntidade buscarUsuarioAutenticado();
}
