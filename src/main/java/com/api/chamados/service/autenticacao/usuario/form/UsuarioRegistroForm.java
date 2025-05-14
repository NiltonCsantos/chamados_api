package com.api.chamados.service.autenticacao.usuario.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record UsuarioRegistroForm(
        @NotBlank(message = "O campo nome deve ser preenchido")
        String usuTxNome,
        @NotBlank(message = "O campo email deve ser preenchido")
        @Email(message = "email inválido")
        String usuTxEmail,
        @NotBlank(message = "O campo senha deve ser preenchido")
        @Size(min = 8, message = "a senha deve ter no mínimo 8 caracteres")
        String usuTxSenha,

        @NotBlank(message = "O cnpj não deve estar em branco", groups = {EmpresaGroup.class})
        @CNPJ(message = "Cnpj inválido", groups = {EmpresaGroup.class})
        String empTxCnpj,
        @NotBlank(message = "O cep não deve ser nulo", groups = {EmpresaGroup.class})
        @Size(min = 8, max = 8, message = "O cep deve ter 8 números", groups = {EmpresaGroup.class})
        String empTxCep,
        @NotNull(message = "O municipio não deve ser nulo", groups = {EmpresaGroup.class})
        Long munNrId,
        Long usuNrId
) {
}
