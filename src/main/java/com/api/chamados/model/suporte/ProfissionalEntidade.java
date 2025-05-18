package com.api.chamados.model.suporte;


import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(of = "proNrId")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pro_profissional", schema = "suporte")
public class ProfissionalEntidade {

    @Id
    @Column(name = "pro_nr_id")
    private Long proNrId;

    @Column(name = "pro_tx_cpf")
    private String proTxCpf;

    @Column(name = "pro_tx_celular")
    private String proTxCelular;

    @Column(name = "eqi_nr_id")
    private Long eqiNrId;
}
