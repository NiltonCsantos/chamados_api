package com.api.chamados.model.suporte;

import com.api.chamados.model.suporte.enums.EquipeEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(of = "eqiNrId")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "eqi_equipe", schema = "suporte")
public class EquipeEntidade {
    @Id
    @Column(name = "eqi_nr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eqiNrId;

    @Column(name = "equi_tx_nome")
    @Enumerated(EnumType.STRING)
    private EquipeEnum eqiTxNome;
}
