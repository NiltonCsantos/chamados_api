package com.api.chamados.model.atendimento;

import com.api.chamados.model.atendimento.enums.StatusChamadoEnum;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "chaNrId")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cha_chamado", schema = "atendimento")
public class ChamadoEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cha_nr_id")
    private Long chaNrId;

    @Column(name = "cha_tx_ultimo_status")
    @Enumerated(EnumType.STRING)
    private StatusChamadoEnum chaTxUltimoStatus;
    @Column(name = "cha_tx_descricao")
    private String chaTxDescricao;
    @Column(name = "cha_dt_abertura")
    private LocalDateTime chaDtAbertura;
    @Column(name = "cha_dt_fechamento")
    private LocalDateTime chaDtFechamento;
    @Column(name = "eqi_nr_id")
    private Long eqiNrId;
    @Column(name = "emp_nr_id")
    private Long empNrId;
    @Column(name = "cha_tx_titulo")
    private String chaTxTitulo;

}
