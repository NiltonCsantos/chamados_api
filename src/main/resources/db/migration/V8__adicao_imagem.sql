alter table if exists atendimento.cha_chamado
    add column if not exists cha_tx_image varchar;