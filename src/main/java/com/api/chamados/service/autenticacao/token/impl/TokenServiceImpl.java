package com.api.chamados.service.autenticacao.token.impl;

import com.api.chamados.service.autenticacao.token.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${api.token.secret}")
    private String secret;


    @Override
    public String gerarToken(UserDetails usuario, Map<String, Object> claims) {
        return Jwts.builder()
                .setIssuer("auth-feira-app")
                .setSubject(usuario.getUsername())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5184000000L)) // 10 horas
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String gerarTokenTemporario(UserDetails usuario) {
        return Jwts.builder()
                .setIssuer("auth-feira-app")
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String gerarRefreshToken(UserDetails usuario) {
        return Jwts.builder()
                .setIssuer("auth-feira-app")
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5184000000L)) // 60 dias
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    private Key getSignKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
