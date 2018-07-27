# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table estudo (
  id                            bigint auto_increment not null,
  tipo                          tinyint(1) default 0 not null,
  data                          datetime(6),
  constraint pk_estudo primary key (id)
);

create table tarefa (
  id                            bigint auto_increment not null,
  tipo                          tinyint(1) default 0 not null,
  eficacia_percebida            tinyint(1) default 0 not null,
  eficacia_medida               tinyint(1) default 0 not null,
  cliques                       integer not null,
  tempo                         integer not null,
  constraint pk_tarefa primary key (id)
);

create table token_cadastro (
  id                            bigint auto_increment not null,
  usuario_id                    bigint,
  codigo                        varchar(255),
  constraint uq_token_cadastro_usuario_id unique (usuario_id),
  constraint pk_token_cadastro primary key (id)
);

create table usuario (
  id                            bigint auto_increment not null,
  email                         varchar(255),
  senha                         varchar(255),
  verificado                    tinyint(1) default 0 not null,
  constraint pk_usuario primary key (id)
);

alter table token_cadastro add constraint fk_token_cadastro_usuario_id foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;


# --- !Downs

alter table token_cadastro drop foreign key fk_token_cadastro_usuario_id;

drop table if exists estudo;

drop table if exists tarefa;

drop table if exists token_cadastro;

drop table if exists usuario;

