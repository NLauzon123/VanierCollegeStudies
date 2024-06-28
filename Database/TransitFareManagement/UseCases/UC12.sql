-- UC12
-- Collect and show results deriving from requests made by the actor through use case UC08 to UC10

-- Application of UC08
-- Fare user traffic on yearly and monthly basis, 
-- for a selected year and service (i.e., each bus and train lines, in both directions)
-- A year and a service ID must be provided as parameters to the procedure.
exec MidTermTraffic 2027, 1;
go

-- Application of UC09
-- Request Short Term User Traffic by Service; daily and hourly 
-- on all services individually (i.e., each bus and train lines, in both directions)
-- A date ('yyyy-mm-dd') and a service ID must be provided as parameters to the procedure.
exec ShortTermTrafficProc '2023-02-25', 1;
go

-- Application of UC10
-- Fare user traffic on yearly and monthly basis, for a selected year, a period of years, ending with the selected year, 
-- service and direction of traffic (i.e., each bus and train lines)
-- The end year of the summary and the period in years covered by the summary, 
-- along with the service ID and direction of traffic must be provided as parameters to the procedure.
-- The direction of traffic is either 0, 1 or null, the latter combining both directions 0 and 1.
exec LongTermTrafficProc 2027, 5, 1, null;
go

-- Application of UC08
create or alter procedure MidTermTraffic (@year int, @service int) as
begin
	select @year as 'Year', shortName as 'Service #', longName as 'Name' from Services where serviceID = @service;
	select * from dbo.LongMidTermTraffic (@year, @service);
end
go

-- Application of UC09
create or alter procedure ShortTermTrafficProc (@givenDay datetime, @service int) as
begin
	select @givenDay as 'Day', shortName as 'Service #', longName as 'Name' from Services where serviceID = @service;
	select * from dbo.ShortTermTraffic (@givenDay, @service);
end
go

-- Application of UC10
create or alter procedure LongTermTrafficProc (@givenYear int, @period int, @service int, @direction int) as
begin
	select (@givenYear - @period + 1) as 'Long-term traffic summary from year:', @givenYear as 'To year:', @direction as 'Direction',
	shortName as 'Service #', longName as 'Name' from Services where serviceID = @service;
	select * from dbo.LongTermTraffic (@givenYear, @period, @service, @direction);
end
go