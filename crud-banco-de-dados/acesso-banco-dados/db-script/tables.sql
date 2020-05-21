create table Usuario(
	codigo Integer not null,
	login varchar(10)  not null,
	senha varchar(10)
);

insert into Usuario values (1, 'admin', 'nimda');

create table Pessoa(
	codigo Integer not null,
	nome varchar(30) not null,
	endereco varchar(50) not null
);

alter table Pessoa
	add constraint Pessoa_PK
		primary key(codigo);

insert into Pessoa values (1, 'João da Silva', 'Av. 7 de Setembro, 1254');
insert into Pessoa values (2, 'Maria Joana', 'Rua João vicente, 54');
insert into Pessoa values (3, 'Fábio de Souza', 'Av. Papa João Paulo, 231');
insert into Pessoa values (4, 'Joana Carala', 'Av. Dr. Carlos Souza, 87');
insert into Pessoa values (5, 'Erica Bernandes', 'Rua Timóteo, 654');