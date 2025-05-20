package com.api.chamados.service.base.impl;

import com.api.chamados.config.exceptions.NotFoundException;
import com.api.chamados.model.autenticacao.UsuarioEntidade;
import com.api.chamados.service.base.BaseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
    @Override
    public UsuarioEntidade buscarUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioEntidade) {
            return (UsuarioEntidade) authentication.getPrincipal();
        }
        throw new NotFoundException("Usuario não encontrado");
    }
}
