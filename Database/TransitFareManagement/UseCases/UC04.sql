-- UC04 
-- Long term revenue summaries on a yearly basis, for a selected period of years, 
-- ending with a selected year, globally, online, from buses and by the regions defined by ARTM

-- Yearly sales for a given year and region
create or alter function SalesByYearRegion (@year int, @region int)
returns decimal(14,2) as
begin
	declare @sales decimal(14,2) = 0.00;
	if (@region = 0) -- all: regions, online and buses
		select @sales = sum(fp.price) from FarePurchase fp where year(fp.transationDate) = @year;
	else
	begin
		if (@region > 0) -- any physcial region individually
			select @sales = sum(fp.price) from FarePurchase fp inner join PointsOfSales ps on fp.pointOfSaleID = ps.posID
			where year(fp.transationDate) = @year and ps.r_ID = @region;
		else -- either online (@region = -1) or buses (@region = -2)
			select @sales = sum(fp.price) from FarePurchase fp inner join PointsOfSales ps on fp.pointOfSaleID = ps.posID
			where year(fp.transationDate) = @year and ps.posID = -@region;
	end
	return @sales;
end
go

-- Yearly sales for all the years in the period selected
create or alter function SalesLongTermRegion (@yearEnd int, @period int, @region int)
returns @totSales table (yearVal int, sales decimal(14,2)) as
begin
	declare @count int = @yearEnd - @period + 1;
	while (@count <= @yearEnd)
	begin
		insert into @totSales (yearVal,sales) values (@count,dbo.SalesByYearRegion (@count, @region));
		set @count = @count + 1;
	end
	return
end
go

-- Table that includes all sales online, from buses, and regions, for the selected period
create or alter function CompleteSalesTable (@yearEnd int, @period int) returns table as
return select t1.yearVal 'Year', t1.sales 'Online', t2.sales 'Buses', t3.sales 'Region 1', t4.sales 'Region 2', 
t5.sales 'Region 3', t6.sales 'Region 4', t7.sales 'Region 5', t8.sales 'Region 6', t9.sales 'Region 7',
t10.sales 'Region 8', t11.sales 'All'
from dbo.SalesLongTermRegion (@yearEnd, @period, -1) t1
inner join dbo.SalesLongTermRegion (@yearEnd, @period, -2) t2 on t1.yearVal = t2.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 1) t3 on t1.yearVal = t3.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 2) t4 on t1.yearVal = t4.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 3) t5 on t1.yearVal = t5.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 4) t6 on t1.yearVal = t6.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 5) t7 on t1.yearVal = t7.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 6) t8 on t1.yearVal = t8.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 7) t9 on t1.yearVal = t9.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 8) t10 on t1.yearVal = t10.yearVal
inner join dbo.SalesLongTermRegion (@yearEnd, @period, 0) t11 on t1.yearVal = t11.yearVal;
go
