-- UC10
-- Fare user traffic on yearly and monthly basis, for a selected year, a period of years, ending with the selected year, 
-- service and direction of traffic (i.e., each bus and train lines)

-- Yearly and monthly user traffic for a given year, service and direction
create or alter function YearlyAndMonthlyTraffic (@givenYear int, @service int, @direction int)
returns @traffic table (Year int, WholeYear int, January int, February int, March int, April int, May int,
June int, July int, August int, September int, October int, November int, December int) as
begin
	insert into @traffic values (@givenYear,
	dbo.AnnualTrafficDirection (@givenYear, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 1, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 2, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 3, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 4, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 5, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 6, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 7, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 8, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 9, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 10, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 11, @service, @direction),
	dbo.MonthlyTrafficDirection (@givenYear, 12, @service, @direction));
	return
end
go

-- Yearly and monthly traffic for all the years in the period selected, for the selected service and direction
create or alter function LongTermTraffic (@yearEnd int, @period int, @service int, @direction int)
returns @traffic table (Year int, WholeYear int, January int, February int, March int, April int, May int,
June int, July int, August int, September int, October int, November int, December int) as
begin
	declare @count int = @yearEnd - @period + 1;
	while (@count <= @yearEnd)
	begin
		insert into @traffic select * from dbo.YearlyAndMonthlyTraffic (@count, @service, @direction);
		set @count = @count + 1;
	end
	return
end
go
