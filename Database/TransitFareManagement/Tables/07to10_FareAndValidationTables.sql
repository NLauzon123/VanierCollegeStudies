create table FareUser (
fareUserID integer primary key not null,
mediaType varchar(20) not null,
startDate datetime not null,
endDate datetime not null,
)

create table Fare (
fareID integer primary key not null,
fareUserID integer constraint fk_fare_user_id references FareUser (fareUserID),
fareType varchar(20) not null,
startDate datetime not null,
endDate datetime,
quantityRemaining integer not null,
)

create table FarePurchase (
farePurchaseID integer primary key not null,
fareID integer constraint fk_fare_id references Fare (fareID),
price decimal(6,2) not null,
pointOfSaleID integer constraint fk_pos_id references PointsOfSales (posID),
transationDate datetime not null
)

create table FareValidation (
fareValidationID integer primary key not null,
fareID integer constraint fk_validation_fare_id references Fare (fareID) not null,
validationTime datetime not null,
serviceID integer constraint fk_service_id references Services (serviceID) not null,
direction integer
)