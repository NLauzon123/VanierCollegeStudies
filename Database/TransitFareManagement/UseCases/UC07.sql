-- UC07
-- Revenue details for a selected year, on a yearly and monthly bases, by points of sales

-- Revenue details for the full year for a given fare point of sales
create or alter function SalesYearPointOfSales (@year int, @pos int)
returns decimal(14,2) as
begin
	declare @sales decimal(14,2) = 0;
	select @sales = sum(price) from FarePurchase 
	where year(transationDate) = @year and pointOfSaleID = @pos;
	return @sales;
end
go

-- Revenue details for a month for a given fare point of sales
create or alter function SalesMonthPointOfSales (@year int, @month int, @pos int)
returns decimal(14,2) as
begin
	declare @sales decimal(14,2) = 0;
	select @sales = sum(price) from FarePurchase 
	where year(transationDate) = @year and month(transationDate) = @month and pointOfSaleID = @pos;
	return @sales;
end
go

-- Revenue details for full year and all months for a given point of sales
create or alter function SalesPointOfSales (@year int, @pos int)
returns @sales table (PointOfSaleID int, January decimal(14,2), February decimal(14,2), March decimal(14,2),
April decimal(14,2), May decimal(14,2), June decimal(14,2), July decimal(14,2), August decimal(14,2),
September decimal(14,2), October decimal(14,2), November decimal(14,2), December decimal(14,2), TotalYear decimal(14,2)) as
begin
	insert into @sales values (@pos,
	dbo.SalesMonthPointOfSales (@year, 1, @pos),dbo.SalesMonthPointOfSales (@year, 2, @pos),
	dbo.SalesMonthPointOfSales (@year, 3, @pos),dbo.SalesMonthPointOfSales (@year, 4, @pos),
	dbo.SalesMonthPointOfSales (@year, 5, @pos),dbo.SalesMonthPointOfSales (@year, 6, @pos),
	dbo.SalesMonthPointOfSales (@year, 7, @pos),dbo.SalesMonthPointOfSales (@year, 8, @pos),
	dbo.SalesMonthPointOfSales (@year, 9, @pos),dbo.SalesMonthPointOfSales (@year, 10, @pos),
	dbo.SalesMonthPointOfSales (@year, 11, @pos),dbo.SalesMonthPointOfSales (@year, 12, @pos),
	dbo.SalesYearPointOfSales (@year, @pos));
	return;
end
go

-- Revenue details for a given year for all points of sales
create or alter function CompleteSalesPointOfSale (@year int)
returns @sales table (PointOfSaleID int, January decimal(14,2), February decimal(14,2), March decimal(14,2),
April decimal(14,2), May decimal(14,2), June decimal(14,2), July decimal(14,2), August decimal(14,2),
September decimal(14,2), October decimal(14,2), November decimal(14,2), December decimal(14,2), TotalYear decimal(14,2)) as
begin
	declare @count int = 1;
	while (@count <= 783)
	begin
		insert into @sales select * from SalesPointOfSales (@year, @count);
		set @count = @count + 1;
	end
	return;
end
go
