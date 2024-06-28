-- UC11
-- Collect and show results deriving from requests made by the actor through use case UC04 to UC07

-- Application of UC04 
-- Long term revenue summaries on a yearly basis, for a selected period of years, 
-- ending with a selected year, globally, online, from buses and by the regions defined by ARTM.
-- The end year of the summary and the period in years covered by the summary 
-- must be provided as parameters to the procedure.
exec LongTermRevenueSummary 2027, 5;
go

-- Application of UC05
-- Mid term revenue summaries on a monthly basis for a selected year, 
-- globally, online, from buses and by the regions defined by ARTM.
-- A year must be provided as a parameter to the procedure.
exec MidTermRevenueSummary 2027;
go

-- Application of UC06
-- Revenue details for a selected year, on a yearly and monthly bases, by fare products.
-- A year must be provided as a parameter to the procedure.
exec RevenueDetailsFareProducts 2027;
go

-- Application of UC07
-- Revenue details for a selected year, on a yearly and monthly bases, by points of sales.
-- A year must be provided as a parameter to the procedure.
exec RevenueDetailsPOS 2027;
go

-- Application of UC04 
create or alter procedure LongTermRevenueSummary (@endYear int, @period int) as
begin
	select (@endYear - @period + 1) as 'Long-term revenue summary from year:', @endYear as 'To year:';
	select * from dbo.CompleteSalesTable (@endYear, @period);
end
go
-- Application of UC05
create or alter procedure MidTermRevenueSummary (@year int) as
begin
	select @year as 'Mid-term revenue summary for year:';
	select * from CompleteSalesMidTerm (@year);
end
go

-- Application of UC06
create or alter procedure RevenueDetailsFareProducts (@year int) as
begin
	select @year as 'Sales by fare products for year:';
	select * from dbo.CompleteSalesProduct (@year);
end
go

-- Application of UC07
create or alter procedure RevenueDetailsPOS (@year int) as
begin
	select @year as 'Sales at points of sale for year:';
	select pos.Name, PointOfSaleID, January, February, March, April, May, June, July, August,
	September, October, November, December, TotalYear from CompleteSalesPointOfSale (@year)
	inner join PointsOfSales pos on pos.posID = PointOfSaleID;
end
go

