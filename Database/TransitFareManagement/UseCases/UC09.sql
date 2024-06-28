-- UC09 
-- Request Short Term User Traffic by Service; daily and hourly 
-- on all services individually (i.e., each bus and train lines, in both directions)
create or alter function HourlyTrafficDirection (@givenDay datetime, @givenHour int, @service int, @direction int)
returns int as
begin
	declare @traffic int = 0;
	if (@direction is null)
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = year(@givenDay) and
		month(ValidationTime) = month(@givenDay) and
		day(ValidationTime) = day(@givenDay) and 
		datepart(hour, ValidationTime) = @givenHour and serviceID = @service;
	else
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = year(@givenDay) and 
		month(ValidationTime) = month(@givenDay) and 
		day(ValidationTime) = day(@givenDay) and 
		datepart(hour, ValidationTime) = @givenHour and serviceID = @service and direction = @direction;
	return @traffic;
end
go

create or alter function DailyTrafficDirection (@givenDay datetime, @service int, @direction int)
returns int as
begin
	declare @traffic int = 0;
	if (@direction is null)
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = year(@givenDay) and
		month(ValidationTime) = month(@givenDay) and
		day(ValidationTime) = day(@givenDay) and serviceID = @service;
	else
		select @traffic = count(FareValidationID) from FareValidation
		where year(ValidationTime) = @givenDay and 
		month(ValidationTime) = month(@givenDay) and 
		day(ValidationTime) = day(@givenDay) and serviceID = @service and direction = @direction;
	return @traffic;
end
go

create or alter function DailyTraffic (@givenDay datetime, @service int)
returns @traffic table (period varchar(20), total int, direction0 int, direction1 int) as
begin
	insert into @traffic (period, total, direction0, direction1) values ('Daily',
	dbo.DailyTrafficDirection (@givenDay, @service, null),
	dbo.DailyTrafficDirection (@givenDay, @service, 0),
	dbo.DailyTrafficDirection (@givenDay, @service, 1));
	return;
end
go

create or alter function HourlyTraffic (@givenDay datetime, @givenHour int, @service int)
returns @traffic table (period varchar(20), total int, direction0 int, direction1 int) as
begin
	declare @period varchar(20);
	if (@givenHour = 0) set @period = '0AM';
	if (@givenHour = 1) set @period = '1AM';
	if (@givenHour = 2) set @period = '2AM';
	if (@givenHour = 3) set @period = '3AM';
	if (@givenHour = 4) set @period = '4AM';
	if (@givenHour = 5) set @period = '5AM';
	if (@givenHour = 6) set @period = '6AM';
	if (@givenHour = 7) set @period = '7AM';
	if (@givenHour = 8) set @period = '8AM';
	if (@givenHour = 9) set @period = '9AM';
	if (@givenHour = 10) set @period = '10AM';
	if (@givenHour = 11) set @period = '11AM';
	if (@givenHour = 12) set @period = '12AM';
	if (@givenHour = 13) set @period = '1PM';
	if (@givenHour = 14) set @period = '2PM';
	if (@givenHour = 15) set @period = '3PM';
	if (@givenHour = 16) set @period = '4PM';
	if (@givenHour = 17) set @period = '5PM';
	if (@givenHour = 18) set @period = '6PM';
	if (@givenHour = 19) set @period = '7PM';
	if (@givenHour = 20) set @period = '8PM';
	if (@givenHour = 21) set @period = '9PM';
	if (@givenHour = 22) set @period = '10PM';
	if (@givenHour = 23) set @period = '11PM';
	insert into @traffic (period, total, direction0, direction1) values (@period,
	dbo.HourlyTrafficDirection (@givenDay, @givenHour, @service, null),
	dbo.HourlyTrafficDirection (@givenDay, @givenHour, @service, 0),
	dbo.HourlyTrafficDirection (@givenDay, @givenHour, @service, 1));
	return;
end
go

create or alter function ShortTermTraffic (@givenDay datetime, @service int)
returns @traffic table (period varchar(20), total int, direction0 int, direction1 int) as
begin
	insert into @traffic select * from dbo.DailyTraffic (@givenDay, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 0, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 1, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 2, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 3, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 4, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 5, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 6, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 7, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 8, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 9, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 10, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 11, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 12, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 13, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 14, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 15, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 16, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 17, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 18, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 19, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 20, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 21, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 22, @service);
	insert into @traffic select * from dbo.HourlyTraffic (@givenDay, 23, @service);
	return
end
go

select * from dbo.ShortTermTraffic ('2027-01-01', 1);
go
