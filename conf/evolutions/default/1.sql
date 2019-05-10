# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table estudo (
  id                            bigserial not null,
  tipo                          boolean default false not null,
  usuario_id                    bigint,
  data                          timestamptz,
  token_id                      bigint,
  constraint uq_estudo_token_id unique (token_id),
  constraint pk_estudo primary key (id)
);

create table evento (
  id                            bigserial not null,
  nome                          varchar(255),
  sigla                         varchar(255),
  descricao                     TEXT,
  programa                      TEXT,
  informacoes                   TEXT,
  local                         varchar(255),
  data                          integer not null,
  preco                         float not null,
  categoria                     varchar(255),
  constraint pk_evento primary key (id)
);

create table relatorio_estudo (
  id                            bigserial not null,
  estudo_id                     bigint,
  tempo                         bigint,
  cliques                       bigint,
  percebida                     bigint,
  medida                        bigint,
  satisfacao                    bigint,
  constraint uq_relatorio_estudo_estudo_id unique (estudo_id),
  constraint pk_relatorio_estudo primary key (id)
);

create table sus (
  id                            bigserial not null,
  estudo_id                     bigint,
  q1                            bigint,
  q2                            bigint,
  q3                            bigint,
  q4                            bigint,
  q5                            bigint,
  q6                            bigint,
  q7                            bigint,
  q8                            bigint,
  q9                            bigint,
  q10                           bigint,
  total                         float,
  constraint uq_sus_estudo_id unique (estudo_id),
  constraint pk_sus primary key (id)
);

create table tarefa (
  id                            bigserial not null,
  codigo                        varchar(255),
  estudo_id                     bigint,
  evento                        bigint,
  data_hora_inicio              timestamptz,
  data_hora_fim                 timestamptz,
  cliques                       bigint,
  concluido_real                boolean default false not null,
  concluido_percebido           boolean default false not null,
  constraint pk_tarefa primary key (id)
);

create table token_cadastro (
  id                            bigserial not null,
  usuario_id                    bigint,
  codigo                        varchar(255),
  constraint uq_token_cadastro_usuario_id unique (usuario_id),
  constraint pk_token_cadastro primary key (id)
);

create table token_sistema (
  id                            bigserial not null,
  usuario_id                    bigint,
  codigo                        varchar(255),
  expiracao                     timestamptz,
  constraint uq_token_sistema_usuario_id unique (usuario_id),
  constraint pk_token_sistema primary key (id)
);

create table usuario (
  id                            bigserial not null,
  email                         varchar(255),
  senha                         varchar(255),
  verificado                    boolean default false not null,
  constraint pk_usuario primary key (id)
);

create index ix_estudo_usuario_id on estudo (usuario_id);
alter table estudo add constraint fk_estudo_usuario_id foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;

alter table estudo add constraint fk_estudo_token_id foreign key (token_id) references token_sistema (id) on delete restrict on update restrict;

alter table relatorio_estudo add constraint fk_relatorio_estudo_estudo_id foreign key (estudo_id) references estudo (id) on delete restrict on update restrict;

alter table sus add constraint fk_sus_estudo_id foreign key (estudo_id) references estudo (id) on delete restrict on update restrict;

create index ix_tarefa_estudo_id on tarefa (estudo_id);
alter table tarefa add constraint fk_tarefa_estudo_id foreign key (estudo_id) references estudo (id) on delete restrict on update restrict;

alter table token_cadastro add constraint fk_token_cadastro_usuario_id foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;

alter table token_sistema add constraint fk_token_sistema_usuario_id foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists estudo drop constraint if exists fk_estudo_usuario_id;
drop index if exists ix_estudo_usuario_id;

alter table if exists estudo drop constraint if exists fk_estudo_token_id;

alter table if exists relatorio_estudo drop constraint if exists fk_relatorio_estudo_estudo_id;

alter table if exists sus drop constraint if exists fk_sus_estudo_id;

alter table if exists tarefa drop constraint if exists fk_tarefa_estudo_id;
drop index if exists ix_tarefa_estudo_id;

alter table if exists token_cadastro drop constraint if exists fk_token_cadastro_usuario_id;

alter table if exists token_sistema drop constraint if exists fk_token_sistema_usuario_id;

drop table if exists estudo cascade;

drop table if exists evento cascade;

drop table if exists relatorio_estudo cascade;

drop table if exists sus cascade;

drop table if exists tarefa cascade;

drop table if exists token_cadastro cascade;

drop table if exists token_sistema cascade;

drop table if exists usuario cascade;

