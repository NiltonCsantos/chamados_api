alter table if exists atendimento.hic_historico_chamado
    alter column hic_tx_status type varchar(56);

alter table if exists atendimento.hic_historico_chamado
    alter column drh_tx_justificativa drop not null;