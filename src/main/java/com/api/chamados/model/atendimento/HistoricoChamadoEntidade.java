package com.api.chamados.model.atendimento;

import com.api.chamados.model.atendimento.enums.StatusChamadoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "hicNrId")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hic_historico_chamado", schema = "atendimento")
public class HistoricoChamadoEntidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hic_nr_id")
    private Long hicNrId;

    @Column(name = "drh_tx_justificativa")
    private String drhTxJustificativa;
    @Column(name = "drh_dt_atualizacao")
    private LocalDateTime chaDtAtualizacao;
    @Column(name = "hic_tx_status")
    @Enumerated(EnumType.STRING)
    private StatusChamadoEnum hicTxStatus;
    @Column(name = "cha_nr_id")
    private Long chaNrId;
    @Column(name = "usu_nr_id")
    private Long usuNrId;
}
