--tabelas

create table if not exists suporte.cbo_classificacao_brasileira_de_ocupacao (
    cbo_nr_id bigserial not null,
    cbo_tx_codigo varchar(50) not null,
    cbo_tx_descricao varchar(150) not null,
    constraint cbo_classificacao_brasileira_de_ocupacao_pk primary key (cbo_nr_id)
);

create table if not exists atendimento.cha_chamado (
    cha_nr_id bigserial not null,
    cha_tx_ultimo_status varchar(26) not null,
    cha_tx_descricao varchar(512) not null,
    cha_dt_abertura timestamp not null,
    cha_dt_fechamento timestamp not null,
    pro_nr_id bigint not null,
    emp_nr_id bigint not null,
    constraint cha_chamado_pk primary key (cha_nr_id)
);

create table if not exists atendimento.emp_empresa (
    emp_nr_id bigint not null,
    emp_tx_cnpj varchar(14) not null,
    emp_tx_cep varchar(8) not null,
    mun_nr_id bigint not null,
    constraint emp_empresa_pk primary key (emp_nr_id)
);

create table if not exists suporte.eqi_equipe (
   eqi_nr_id bigserial not null,
   equi_tx_nome varchar(512) not null,
   constraint eqi_equipe_pk primary key (eqi_nr_id)
);

create table if not exists endereco.est_estado (
    est_nr_id bigserial not null,
    est_tx_nome varchar(256) not null,
    est_tx_sigla varchar(2) not null,
    constraint est_estado_pk primary key (est_nr_id)
);

create table if not exists atendimento.hic_historico_chamado (
    hic_nr_id bigserial not null,
    drh_tx_justificativa varchar(512) not null,
    drh_dt_atualizacao timestamp not null,
    hic_tx_status timestamp not null,
    cha_nr_id bigint not null,
    usu_nr_id bigint not null,
    constraint hic_historico_chamado_pk primary key (hic_nr_id)
);

create table if not exists endereco.mun_municipio (
    mun_nr_id bigserial not null,
    mun_tx_nome varchar(512) not null,
    mun_tx_codigo_ibge varchar(56) not null,
    est_nr_id bigint not null,
    constraint mun_municipio_pk primary key (mun_nr_id)
);

create table if not exists autenticacao.per_perfil (
    per_nr_id bigserial not null,
    per_tx_nome varchar(126) not null,
    constraint per_perfil_pk primary key (per_nr_id)
);

create table if not exists suporte.prc_profissional_cbo (
    prc_nr_id bigserial not null,
    cbo_nr_id bigint not null,
    pro_nr_id bigint not null,
    constraint prc_profissional_cbo_pk primary key (prc_nr_id)
);

create table if not exists suporte.pro_profissional (
    pro_nr_id bigint not null,
    pro_tx_cpf varchar(111) not null,
    pro_tx_cbo bigint not null,
    eqi_nr_id bigint not null,
    constraint pro_profissional_pk primary key (pro_nr_id)
);

create table if not exists autenticacao.usu_usuario (
    usu_nr_id bigserial not null,
    usu_tx_email varchar(256) not null,
    usu_bl_ativo boolean not null,
    usu_tx_senha varchar(256) not null,
    usu_tx_nome varchar(28) not null,
    per_nr_id bigint not null,
    constraint usu_usuario_pk primary key (usu_nr_id)
);

--foreign keys

alter table if exists atendimento.emp_empresa add constraint usu_usuario_emp_empresa
    foreign key (emp_nr_id) references autenticacao.usu_usuario (usu_nr_id);

alter table if exists autenticacao.usu_usuario add constraint usu_usuario_per_perfil
    foreign key (per_nr_id) references autenticacao.per_perfil (per_nr_id);

alter table if exists suporte.pro_profissional add constraint usu_usuario_pro_profissional
    foreign key (pro_nr_id) references autenticacao.usu_usuario (usu_nr_id);

alter table if exists atendimento.cha_chamado add constraint cha_chamado_emp_empresa
    foreign key (emp_nr_id) references atendimento.emp_empresa (emp_nr_id);

alter table if exists atendimento.cha_chamado add constraint cha_chamado_pro_profissional
    foreign key (pro_nr_id) references suporte.pro_profissional(pro_nr_id);

alter table if exists suporte.pro_profissional add constraint pro_profissional_eqi_equipe
    foreign key (eqi_nr_id) references suporte.eqi_equipe (eqi_nr_id);

alter table if exists suporte.prc_profissional_cbo add constraint prc_profissional_cbo_cbo_classificacao_brasileira_de_ocupacao
    foreign key (cbo_nr_id) references suporte.cbo_classificacao_brasileira_de_ocupacao (cbo_nr_id);

alter table if exists suporte.prc_profissional_cbo add constraint prc_profissional_cbo_pro_profissional
    foreign key (pro_nr_id) references suporte.pro_profissional (pro_nr_id);

alter table if exists atendimento.emp_empresa add constraint emp_empresa_mun_municipio
    foreign key (mun_nr_id) references endereco.mun_municipio (mun_nr_id);

alter table if exists endereco.mun_municipio add constraint mun_municipio_est_estado
    foreign key (est_nr_id) references endereco.est_estado (est_nr_id);

alter table if exists atendimento.hic_historico_chamado add constraint hic_historico_chamado_cha_chamado
    foreign key (cha_nr_id) references atendimento.cha_chamado (cha_nr_id);

alter table if exists atendimento.hic_historico_chamado add constraint hic_historico_chamado_usu_usuario
    foreign key (usu_nr_id) references autenticacao.usu_usuario (usu_nr_id);