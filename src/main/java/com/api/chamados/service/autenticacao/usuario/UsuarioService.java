package com.api.chamados.service.autenticacao.usuario;

import com.api.chamados.service.autenticacao.usuario.dto.UsuarioDto;
import com.api.chamados.service.autenticacao.usuario.form.UsuarioEdicaoForm;

public interface UsuarioService {
    void adicionarEnderecoAoUsuario(Long endNrId);;
    UsuarioDto editarDadosUsuario(UsuarioEdicaoForm usuarioEdicaoForm);
}
