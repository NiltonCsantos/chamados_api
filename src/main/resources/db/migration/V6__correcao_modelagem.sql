alter table if exists atendimento.hic_historico_chamado
    drop column if exists usu_nr_id;

alter table if exists atendimento.hic_historico_chamado
    add column if not exists pro_nr_id int8 not null;

alter table if exists atendimento.hic_historico_chamado
    add constraint hic_historico_chamado_pro_fkey foreign key (pro_nr_id) references suporte.pro_profissional(pro_nr_id);