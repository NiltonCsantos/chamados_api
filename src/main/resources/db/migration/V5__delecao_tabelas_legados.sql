drop table if exists
    suporte.prc_profissional_cbo;

drop table if exists
    suporte.cbo_classificacao_brasileira_de_ocupacao;

alter table if exists suporte.pro_profissional
    drop column if exists pro_tx_cbo;

alter table if exists suporte.pro_profissional
    add column if not exists pro_tx_celular varchar(11);