create table Agency (
agencyID integer primary key not null,
vocation varchar(15) not null,
shortName varchar(10) not null,
longName varchar(100) not null
);

insert into Agency values (1,'Oversight','ARTM','Autorit� r�gionale de transport m�tropolitain');
insert into Agency values (2,'Operator','STM','Soci�t� de transport de Montr�al');
insert into Agency values (3,'Operator','EXO','R�seau de transport m�tropolitain');
insert into Agency values (4,'Operator','RTL','Soci�t� de transport de Longueuil');
insert into Agency values (5,'Operator','STL','Soci�t� de transport de Laval');
insert into Agency values (6,'Operator','REM','R�seau express m�tropolitain');
