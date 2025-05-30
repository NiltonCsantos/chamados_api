package com.api.chamados.service.autenticacao.authenticate.impl;


import com.api.chamados.model.autenticacao.CustomUserDetails;
import com.api.chamados.model.autenticacao.UsuarioEntidade;
import com.api.chamados.model.autenticacao.enums.PerfilEnum;
import com.api.chamados.model.atendimento.EmpresaEntidade;
import com.api.chamados.repository.autenticacao.PerfilRepository;
import com.api.chamados.repository.autenticacao.UsuarioRepository;
import com.api.chamados.repository.endereco.MunicipioRepository;
import com.api.chamados.repository.suporte.EmpresaRepository;
import com.api.chamados.service.autenticacao.authenticate.AuthenticationService;
import com.api.chamados.service.autenticacao.authenticate.UserDetailsServiceImpl;
import com.api.chamados.service.autenticacao.authenticate.dto.AuthDto;
import com.api.chamados.service.autenticacao.authenticate.dto.InformacaoUsuarioDto;
import com.api.chamados.service.autenticacao.token.TokenService;
import com.api.chamados.service.autenticacao.usuario.form.UsuarioLoginForm;
import com.api.chamados.service.autenticacao.usuario.form.UsuarioRegistroForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticationService {


    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PerfilRepository perfilRepository;
    private final TokenService tokenService;
    private final UserDetailsServiceImpl authorizationService;
    private final EmpresaRepository empresaRepository;
    private final MunicipioRepository municipioRepository;
//   private final MailService emailService;


    @Override
    public void salvarEmpresa(UsuarioRegistroForm form) {

        if (form.usuNrId() == null && empresaRepository.existsByEmpTxCnpj(form.empTxCnpj())) {
            throw new RuntimeException("Empresa já cadastrada");
        }

        if (!municipioRepository.existsById(form.munNrId()))
            throw new RuntimeException("Municipio não econtrado cadastrado");

        var usuario = salvarUsuario(form, PerfilEnum.EMPRESA);

        var empresaEntidade = EmpresaEntidade
                .builder()
                .empNrId(usuario.getUsuNrId())
                .build();

        empresaEntidade.setEmpTxCnpj(form.empTxCnpj());
        empresaEntidade.setEmpTxCep(form.empTxCep());
        empresaEntidade.setMunNrId(form.munNrId());

        empresaRepository.save(empresaEntidade);
    }

    @Override
    public void salvarUsuario(UsuarioRegistroForm form) {
    }

    private UsuarioEntidade salvarUsuario(UsuarioRegistroForm form, PerfilEnum perTxNome) {

        if (form.usuNrId() == null && this.usuarioRepository.findByUsuTxEmail(form.usuTxEmail()) != null) {
            throw new RuntimeException();
        }

        var perfil = perfilRepository.findByPerfilUsuario(perTxNome)
                .orElseThrow();

        String encryptedPassword = new BCryptPasswordEncoder().encode(form.usuTxSenha());

        var usuario = form.usuNrId() != null ?
                usuarioRepository.findById(form.usuNrId())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"))
                : UsuarioEntidade.
                builder()
                .perfil(perfil)
                .usuTxEmail(form.usuTxEmail())
                .build();

        usuario.setUsuTxNome(form.usuTxNome());
        usuario.setUsuTxSenha(encryptedPassword);
        usuarioRepository.save(usuario);
        var token = tokenService.gerarTokenTemporario(usuario);
//                emailService.sendMail(usuario.getUsuTxEmail(), usuario.getUsername(), token);
        return usuario;
    }

    @Override
    public AuthDto fazerLogin(UsuarioLoginForm form) {

        if (authorizationService.loadUserByUsername(form.usuTxEmail()) == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                form.usuTxEmail(), form.usuTxSenha());

        var auth = this.authenticationManager.authenticate(passwordAuthenticationToken);

        var claims = preencherClaims((UsuarioEntidade) auth.getPrincipal());
        String acessToken = this.tokenService.gerarToken((UserDetails) auth.getPrincipal(), claims);
        String refreshToken = this.tokenService.gerarRefreshToken((UserDetails) auth.getPrincipal());

        return new AuthDto(acessToken, refreshToken);
    }

    //todo verificar
    @Override
    public AuthDto fazerLoginComToken(String refrashToken) {

        String email = tokenService.validarToken(refrashToken);

        CustomUserDetails user = usuarioRepository.findByUsuTxEmail(email);

        if (user == null) {
            throw new RuntimeException("Ocorreu um erro ao buscar o usuário");
        }


        var claims = preencherClaims((UsuarioEntidade) user);

        String acessToken = this.tokenService.gerarToken((UserDetails) user, claims);
        String refreshToken = this.tokenService.gerarRefreshToken((UserDetails) user);
        return new AuthDto(acessToken, refreshToken);
    }

    @Override
    public String ativarConta(String token) {
        var email = tokenService.validarToken(token);
        var usuario = (UsuarioEntidade) usuarioRepository.findByUsuTxEmail(email);
        usuario.setUsublAtivo(true);
        usuarioRepository.save(usuario);
        return "Conta ativa com sucesso!";
    }

    private Map<String, Object> preencherClaims(UsuarioEntidade usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usuario", InformacaoUsuarioDto.of(usuario));
        return claims;
    }

}
