package com.api.chamados.model.autenticacao;

import com.api.chamados.model.autenticacao.enums.PerfilEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "per_perfil", schema = "autenticacao")
@EqualsAndHashCode(of = "perNrId")
public class PerfilEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_nr_id")
    private Long perNrId;

    @Column(name = "per_tx_nome")
    @Enumerated(EnumType.STRING)
    private PerfilEnum pertxNome;

    @OneToMany(mappedBy = "perfil")
    private List<UsuarioEntidade> usuario;

}
