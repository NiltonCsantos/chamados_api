alter table if exists atendimento.hic_historico_chamado
    rename column drh_tx_justificativa to hic_tx_justificativa;

alter table if exists atendimento.hic_historico_chamado
    rename column drh_dt_atualizacao to hic_dt_atualizacao;