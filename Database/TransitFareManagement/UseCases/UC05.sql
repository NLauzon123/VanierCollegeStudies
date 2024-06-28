-- UC05
-- Mid term revenue summaries on a monthly basis for a selected year, 
-- globally, online, from buses and by the regions defined by ARTM

-- Monthly sales for a given year, month and region
create or alter function SalesByMonthRegion (@year int, @month int, @region int)
returns decimal(14,2) as
begin
	declare @sales decimal(14,2) = 0.00;
	if (@region = 0) -- all: regions, online and buses
		select @sales = sum(fp.price) from FarePurchase fp where year(fp.transationDate) = @year and month(fp.transationDate) = @month;
	else
	begin
		if (@region > 0) -- any physcial region individually
			select @sales = sum(fp.price) from FarePurchase fp inner join PointsOfSales ps on fp.pointOfSaleID = ps.posID
			where year(fp.transationDate) = @year and  month(fp.transationDate) = @month and ps.r_ID = @region;
		else -- either online (@region = -1) or buses (@region = -2)
			select @sales = sum(fp.price) from FarePurchase fp inner join PointsOfSales ps on fp.pointOfSaleID = ps.posID
			where year(fp.transationDate) = @year and  month(fp.transationDate) = @month and ps.posID = -@region;
	end
	return @sales;
end
go

-- Monthly sales for all months, for a given year and region
create or alter function SalesMidTermRegion (@year int, @region int)
returns @sales table (Region varchar(20), January decimal(14,2), February decimal(14,2), March decimal(14,2),
April decimal(14,2), May decimal(14,2), June decimal(14,2), July decimal(14,2), August decimal(14,2),
September decimal(14,2), October decimal(14,2), November decimal(14,2), December decimal(14,2)) as
begin
	declare @regionName varchar(20);
	if (@region = -1) set @regionName = 'Online';
	if (@region = -2) set @regionName = 'Buses';
	if (@region = 1) set @regionName = 'Region 1';
	if (@region = 2) set @regionName = 'Region 2';
	if (@region = 3) set @regionName = 'Region 3';
	if (@region = 4) set @regionName = 'Region 4';
	if (@region = 5) set @regionName = 'Region 5';
	if (@region = 6) set @regionName = 'Region 6';
	if (@region = 7) set @regionName = 'Region 7';
	if (@region = 8) set @regionName = 'Region 8';
	if (@region = 0) set @regionName = 'Total';
	insert into @sales values (@regionName,
	dbo.SalesByMonthRegion (@year, 1, @region),dbo.SalesByMonthRegion (@year, 2, @region),
	dbo.SalesByMonthRegion (@year, 3, @region),dbo.SalesByMonthRegion (@year, 4, @region),
	dbo.SalesByMonthRegion (@year, 5, @region),dbo.SalesByMonthRegion (@year, 6, @region),
	dbo.SalesByMonthRegion (@year, 7, @region),dbo.SalesByMonthRegion (@year, 8, @region),
	dbo.SalesByMonthRegion (@year, 9, @region),dbo.SalesByMonthRegion (@year, 10, @region),
	dbo.SalesByMonthRegion (@year, 11, @region),dbo.SalesByMonthRegion (@year, 12, @region));
	return;
end
go

-- Monthly sales for all months and regions, for a given year
create or alter function CompleteSalesMidTerm (@year int)
returns @sales table (Region varchar(20), January decimal(14,2), February decimal(14,2), March decimal(14,2),
April decimal(14,2), May decimal(14,2), June decimal(14,2), July decimal(14,2), August decimal(14,2),
September decimal(14,2), October decimal(14,2), November decimal(14,2), December decimal(14,2)) as
begin
	insert into @sales select * from SalesMidTermRegion (@year, -2);
	insert into @sales select * from SalesMidTermRegion (@year, -1);
	insert into @sales select * from SalesMidTermRegion (@year, 1);
	insert into @sales select * from SalesMidTermRegion (@year, 2);
	insert into @sales select * from SalesMidTermRegion (@year, 3);
	insert into @sales select * from SalesMidTermRegion (@year, 4);
	insert into @sales select * from SalesMidTermRegion (@year, 5);
	insert into @sales select * from SalesMidTermRegion (@year, 6);
	insert into @sales select * from SalesMidTermRegion (@year, 7);
	insert into @sales select * from SalesMidTermRegion (@year, 8);
	insert into @sales select * from SalesMidTermRegion (@year, 0);
	return;
end
go
