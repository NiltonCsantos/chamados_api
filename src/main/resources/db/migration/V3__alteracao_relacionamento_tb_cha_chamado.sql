alter table if exists atendimento.cha_chamado
    drop column if exists pro_nr_id;

alter table if exists atendimento.cha_chamado
    add column if not exists eqi_nr_id int8 not null;

alter table atendimento.cha_chamado
    add constraint cha_chamadoeqi_fkey foreign key(eqi_nr_id) references suporte.eqi_equipe;

alter table atendimento.cha_chamado
    add column if not exists cha_tx_titulo varchar(256) not null;

alter table if exists atendimento.cha_chamado
    alter column  cha_dt_fechamento drop not null;