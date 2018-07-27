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

create table usuario (
  id                            bigint auto_increment not null,
  email                         varchar(255),
  senha                         varchar(255),
  constraint pk_usuario primary key (id)
);


# --- !Downs

drop table if exists estudo;

drop table if exists tarefa;

drop table if exists usuario;

