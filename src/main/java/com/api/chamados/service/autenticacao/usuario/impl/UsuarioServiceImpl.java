package com.api.chamados.service.autenticacao.usuario.impl;

import com.api.chamados.repository.autenticacao.UsuarioRepository;
import com.api.chamados.service.autenticacao.usuario.UsuarioService;
import com.api.chamados.service.autenticacao.usuario.dto.UsuarioDto;
import com.api.chamados.service.autenticacao.usuario.form.UsuarioEdicaoForm;
import com.api.chamados.service.base.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl extends BaseServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;


    @Override
    public void adicionarEnderecoAoUsuario(Long endNrId){
//        var usuNrId = this.buscarUsuarioAutenticado().getUsuNrId();
//        enderecoRepository.findById(endNrId)
//                .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
//
//        var usuarioEnderecoEntidade = UsuarioEnderecoEntidade
//                .builder()
//                .endNrId(endNrId)
//                .usuNrId(usuNrId)
//                .build();
//        usuarioEnderecoRepository.save(usuarioEnderecoEntidade);
    }

    @Override
    public UsuarioDto editarDadosUsuario(UsuarioEdicaoForm form) {
        var usuario = this.buscarUsuarioAutenticado();
        usuario.setUsuTxNome(form.usuTxNome().trim());
        usuarioRepository.save(usuario);
        return new UsuarioDto(usuario);
    }
}
