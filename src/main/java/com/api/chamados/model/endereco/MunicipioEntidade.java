package com.api.chamados.model.endereco;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "mun_municipio", schema = "endereco")
@EqualsAndHashCode(of = "munNrId")
public class MunicipioEntidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mun_nr_id")
    private Long munNrId;

    @Column(name = "mun_tx_nome")
    private String munTxNome;
    @Column(name = "mun_tx_codigo_ibge")
    private String munTxCodigoIbge;
    @Column(name = "est_nr_id")
    private Long estNrId;
}
