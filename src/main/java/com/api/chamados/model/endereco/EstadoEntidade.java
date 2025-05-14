package com.api.chamados.model.endereco;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "est_estado", schema = "endereco")
@EqualsAndHashCode(of = "estNrId")
public class EstadoEntidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "est_nr_id")
    private Long estNrId;

    @Column(name = "est_tx_nome")
    private String estTxNome;
    @Column(name = "est_tx_sigla")
    private String estTxSigla;
}
