create table Agency (
agencyID integer primary key not null,
vocation varchar(15) not null,
shortName varchar(10) not null,
longName varchar(100) not null
);

insert into Agency values (1,'Oversight','ARTM','Autorité régionale de transport métropolitain');
insert into Agency values (2,'Operator','STM','Société de transport de Montréal');
insert into Agency values (3,'Operator','EXO','Réseau de transport métropolitain');
insert into Agency values (4,'Operator','RTL','Société de transport de Longueuil');
insert into Agency values (5,'Operator','STL','Société de transport de Laval');
insert into Agency values (6,'Operator','REM','Réseau express métropolitain');
