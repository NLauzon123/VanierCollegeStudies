-- UC08 
-- Fare user traffic on yearly and monthly basis, 
-- for a selected year and service (i.e., each bus and train lines, in both directions)

-- Annual user count for a given year and service, for a specific direction
create or alter function AnnualTrafficDirection (@givenYear int, @service int, @direction int)
returns int as
begin
	declare @traffic int = 0;
	if (@direction is null) -- the case where no direction is selected
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = @givenYear and serviceID = @service;
	else -- all cases where a direction is selected (0 or 1)
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = @givenYear and serviceID = @service and direction = @direction;
	return @traffic;
end
go

-- Monthly user count for a given year, month and service, for a specific direction
create or alter function MonthlyTrafficDirection (@givenYear int, @givenMonth int, @service int, @direction int)
returns int as
begin
	declare @traffic int = 0;
	if (@direction is null) -- the case where no direction is selected
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = @givenYear and month(ValidationTime) =  @givenMonth and serviceID = @service;
	else -- all cases where a direction is selected (0 or 1)
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = @givenYear and month(ValidationTime) =  @givenMonth and serviceID = @service and direction = @direction;
	return @traffic;
end
go

-- Annual user count for a given year and service, in total and both directions
create or alter function AnnualTraffic (@givenYear int, @service int)
returns @traffic table (period varchar(20), total int, direction0 int, direction1 int) as
begin
	insert into @traffic (period, total, direction0, direction1) values ('Annual',
	dbo.AnnualTrafficDirection (@givenYear, @service, null),
	dbo.AnnualTrafficDirection (@givenYear, @service, 0),
	dbo.AnnualTrafficDirection (@givenYear, @service, 1));
	return;
end
go

-- Monthly user count for a given year, month and service, in total and both directions
create or alter function MonthlyTraffic (@givenYear int, @givenMonth int, @service int)
returns @traffic table (period varchar(20), total int, direction0 int, direction1 int) as
begin
	declare @period varchar(20);
	if (@givenMonth = 1) set @period = 'January';
	if (@givenMonth = 2) set @period = 'February';
	if (@givenMonth = 3) set @period = 'March';
	if (@givenMonth = 4) set @period = 'April';
	if (@givenMonth = 5) set @period = 'May';
	if (@givenMonth = 6) set @period = 'June';
	if (@givenMonth = 7) set @period = 'July';
	if (@givenMonth = 8) set @period = 'August';
	if (@givenMonth = 9) set @period = 'September';
	if (@givenMonth = 10) set @period = 'October';
	if (@givenMonth = 11) set @period = 'November';
	if (@givenMonth = 12) set @period = 'December';
	insert into @traffic (period, total, direction0, direction1) values (@period,
	dbo.MonthlyTrafficDirection (@givenYear, @givenMonth, @service, null),
	dbo.MonthlyTrafficDirection (@givenYear, @givenMonth, @service, 0),
	dbo.MonthlyTrafficDirection (@givenYear, @givenMonth, @service, 1));
	return;
end
go

-- Annual and monthly user counts compiled for a given year and service
create or alter function LongMidTermTraffic (@givenYear int, @service int)
returns @traffic table (period varchar(20), total int, direction0 int, direction1 int) as
begin
	insert into @traffic select * from dbo.AnnualTraffic (@givenYear, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 1, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 2, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 3, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 4, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 5, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 6, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 7, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 8, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 9, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 10, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 11, @service);
	insert into @traffic select * from dbo.MonthlyTraffic (@givenYear, 12, @service);
	return
end
go
