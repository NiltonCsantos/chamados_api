package com.api.chamados.model.atendimento;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(of = "empNrId")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "emp_empresa", schema = "atendimento")
public class EmpresaEntidade {
    @Id
    @Column(name = "emp_nr_id")
    private Long empNrId;

    @Column(name = "emp_tx_cnpj")
    private String empTxCnpj;
    @Column(name = "emp_tx_cep")
    private String empTxCep;
    @Column(name = "mun_nr_id")
    private Long munNrId;
}
