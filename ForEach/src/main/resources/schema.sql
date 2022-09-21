CREATE SCHEMA foreachdb;
set search_path = "foreachdb";
create table  root_users (id  bigserial not null, username varchar(255), email varchar(255), password varchar(255), role varchar(255), primary key (id));
create table  users (id  bigserial not null, auth_provider varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), role varchar(255), status varchar(255), primary key (id));
