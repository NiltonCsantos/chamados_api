package com.api.chamados.config.security;

import com.api.chamados.service.autenticacao.token.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsServiceImpl;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token=this.recoveryToken(request);

        if (token != null){

            try {
                String email= tokenService.validarToken(token);

                UserDetails user= userDetailsServiceImpl.loadUserByUsername(email);

                var auth= new UsernamePasswordAuthenticationToken(user, email, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (ExpiredJwtException e){

//                AuthException exception= new AuthException("Token expirado", e);
//                customAuthenticationEntryPoint.commence(request, response, exception);

                return;
            }catch (MalformedJwtException e){

//                AuthException exception= new AuthException("Token mal formado", e);
//                customAuthenticationEntryPoint.commence(request, response, exception);

                SecurityContextHolder.clearContext();
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request){

        String authHeader= request.getHeader("Authorization");

        return authHeader==null
                ? null
                :authHeader.replace("Bearer", "");

    }
}
