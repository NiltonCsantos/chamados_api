package com.api.chamados.service.autenticacao.authenticate.impl;


import com.api.chamados.config.exceptions.BadRaquestException;
import com.api.chamados.config.exceptions.NotFoundException;
import com.api.chamados.model.autenticacao.CustomUserDetails;
import com.api.chamados.model.autenticacao.UsuarioEntidade;
import com.api.chamados.model.autenticacao.enums.PerfilEnum;
import com.api.chamados.model.atendimento.EmpresaEntidade;
import com.api.chamados.model.suporte.ProfissionalEntidade;
import com.api.chamados.repository.autenticacao.PerfilRepository;
import com.api.chamados.repository.autenticacao.UsuarioRepository;
import com.api.chamados.repository.endereco.MunicipioRepository;
import com.api.chamados.repository.atendimento.EmpresaRepository;
import com.api.chamados.repository.suporte.EquipeReposity;
import com.api.chamados.repository.suporte.ProfissionalRepository;
import com.api.chamados.service.autenticacao.authenticate.AuthenticationService;
import com.api.chamados.service.autenticacao.authenticate.UserDetailsServiceImpl;
import com.api.chamados.service.autenticacao.authenticate.dto.AuthDto;
import com.api.chamados.service.autenticacao.authenticate.dto.InformacaoUsuarioDto;
import com.api.chamados.service.autenticacao.token.TokenService;
import com.api.chamados.service.autenticacao.authenticate.form.UsuarioLoginForm;
import com.api.chamados.service.autenticacao.authenticate.form.UsuarioRegistroForm;
import com.api.chamados.service.mail.MailService;
import com.api.chamados.utils.Regex;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private final ProfissionalRepository profissionalRepository;
    private final EquipeReposity equipeReposity;
   private final MailService emailService;


    @Override
    public void salvarEmpresa(UsuarioRegistroForm form, Long empNrId) {

        if (empNrId == null && empresaRepository.existsByEmpTxCnpj(form.empTxCnpj())) {
            throw new BadRaquestException("Empresa já cadastrada");
        }

        if (!municipioRepository.existsById(form.munNrId()))
            throw new NotFoundException("Municipio não econtrado cadastrado");

        var usuario = salvarUsuario(form, PerfilEnum.EMPRESA, empNrId);

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
    public void salvarProfissional(UsuarioRegistroForm form, Long proNrId) {

        if (profissionalRepository.existsByProTxCpfOrProTxCelular(form.proTxCpf(), form.proTxCelular(), proNrId)){
            throw new BadRaquestException("Já existe um profissional com esse CPF ou Celular cadastrado");
        }

        if (!equipeReposity.existsById(form.eqiNrId())){
            throw new NotFoundException("Equipe não encontrada");
        }

        var celularFormatado = Regex.apenasNumeros(form.proTxCelular());
        var cpfFormatado = Regex.apenasNumeros(form.proTxCpf());

        if (celularFormatado.length()<11)
            throw new BadRaquestException("Celular inválido");

        var usuario = salvarUsuario(form, PerfilEnum.EMPRESA, proNrId);

        ProfissionalEntidade profissional = new ProfissionalEntidade();

        profissional.setProNrId(usuario.getUsuNrId());
        profissional.setProTxCpf(cpfFormatado);
        profissional.setProTxCelular(form.proTxCelular());
        profissional.setEqiNrId(form.eqiNrId());
        profissionalRepository.save(profissional);
    }

    private UsuarioEntidade salvarUsuario(UsuarioRegistroForm form, PerfilEnum perTxNome, Long usuNrId) {

        if (usuNrId == null && this.usuarioRepository.findByUsuTxEmail(form.usuTxEmail()) != null) {
            throw new BadRaquestException("Email já cadastrado");
        }

        var perfil = perfilRepository.findByPerfilUsuario(perTxNome)
                .orElseThrow();

        String encryptedPassword = new BCryptPasswordEncoder().encode(form.usuTxSenha() == null? UUID.randomUUID().toString() :form.usuTxSenha());

        var usuario = usuNrId != null ?
                usuarioRepository.findById(usuNrId)
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"))
                : UsuarioEntidade.
                builder()
                .perfil(perfil)
                .usuTxEmail(form.usuTxEmail())
                .build();

        usuario.setUsuTxNome(form.usuTxNome());
        usuario.setUsuTxSenha(encryptedPassword);
        usuarioRepository.save(usuario);
        var token = tokenService.gerarTokenTemporario(usuario);

        if (usuNrId == null) {
            emailService.sendMail(usuario.getUsuTxEmail(), usuario.getUsername(), encryptedPassword, token);
        }

        return usuario;
    }

    @Override
    public AuthDto fazerLogin(UsuarioLoginForm form) {

        if (authorizationService.loadUserByUsername(form.usuTxEmail()) == null) {
            throw new NotFoundException("Usuário não encontrado");
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
            throw new BadRaquestException("Ocorreu um erro ao buscar o usuário");
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
