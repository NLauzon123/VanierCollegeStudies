create table Employees (
employeeID integer identity primary key not null,
agencyID integer not null constraint fk_empAgencyID references Agency (agencyID),
firstName varchar(100) not null,
lastName varchar(100) not null,
username varchar(100) not null,
hashedPassword varbinary(32) not null,
salt varbinary(64) not null
);
