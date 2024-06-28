-- UC06
-- Revenue details for a selected year, on a yearly and monthly bases, by fare products

-- Revenue details for the full year for a given fare product
create or alter function SalesYearProduct (@year int, @product varchar(20))
returns decimal(14,2) as
begin
	declare @sales decimal(14,2) = 0;
	select @sales = sum(fp.price) from FarePurchase fp inner join Fare f on fp.fareID = f.fareID
	where year(fp.transationDate) = @year and f.fareType = @product;
	return @sales;
end
go

-- Revenue details for a month for a given fare product
create or alter function SalesMonthProduct (@year int, @month int, @product varchar(20))
returns decimal(14,2) as
begin
	declare @sales decimal(14,2) = 0;
	select @sales = sum(fp.price) from FarePurchase fp inner join Fare f on fp.fareID = f.fareID
	where year(fp.transationDate) = @year and month(fp.transationDate) = @month and f.fareType = @product;
	return @sales;
end
go

-- Revenue details for full year and all months for a given fare product
create or alter function SalesProduct (@year int, @product varchar(20))
returns @sales table (Product varchar(20), January decimal(14,2), February decimal(14,2), March decimal(14,2),
April decimal(14,2), May decimal(14,2), June decimal(14,2), July decimal(14,2), August decimal(14,2),
September decimal(14,2), October decimal(14,2), November decimal(14,2), December decimal(14,2), TotalYear decimal(14,2)) as
begin
	insert into @sales values (@product,
	dbo.SalesMonthProduct (@year, 1, @product),dbo.SalesMonthProduct (@year, 2, @product),
	dbo.SalesMonthProduct (@year, 3, @product),dbo.SalesMonthProduct (@year, 4, @product),
	dbo.SalesMonthProduct (@year, 5, @product),dbo.SalesMonthProduct (@year, 6, @product),
	dbo.SalesMonthProduct (@year, 7, @product),dbo.SalesMonthProduct (@year, 8, @product),
	dbo.SalesMonthProduct (@year, 9, @product),dbo.SalesMonthProduct (@year, 10, @product),
	dbo.SalesMonthProduct (@year, 11, @product),dbo.SalesMonthProduct (@year, 12, @product),
	dbo.SalesYearProduct (@year, @product));
	return;
end
go

-- Revenue details for a given year for all fare products
create or alter function CompleteSalesProduct (@year int)
returns @sales table (Product varchar(20), January decimal(14,2), February decimal(14,2), March decimal(14,2),
April decimal(14,2), May decimal(14,2), June decimal(14,2), July decimal(14,2), August decimal(14,2),
September decimal(14,2), October decimal(14,2), November decimal(14,2), December decimal(14,2), TotalYear decimal(14,2)) as
begin
	insert into @sales select * from SalesProduct (@year, 'twoTrips');
	insert into @sales select * from SalesProduct (@year, 'tenTrips');
	insert into @sales select * from SalesProduct (@year, 'monthly');
	return;
end
go
