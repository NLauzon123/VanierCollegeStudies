CREATE TYPE PinType AS TABLE ( GivenPin VARCHAR(10) );
GO

-- Experiment with table instead of variable
CREATE or ALTER PROCEDURE DrillEmployeesTableC 
@StartPin PinType READONLY, @end INT = 1 AS
BEGIN
	IF OBJECT_ID('tempdb..#Result') IS NULL
	BEGIN
		CREATE TABLE #Result (
		ManagerPin varchar(10), Pin varchar(10), LegacyPin varchar(10), JobLevel varchar(2), EmpName varchar(20));
	END
	DECLARE @NextPin AS PinType;
	INSERT INTO @NextPin SELECT LegacyPin FROM employees INNER JOIN @StartPin ON ManagerPin = GivenPin WHERE LegacyPin != GivenPin;
	DECLARE @Count INT = (SELECT COUNT(GivenPin) FROM @NextPin);
	IF (@Count > 0)
	BEGIN
		INSERT INTO #Result SELECT ManagerPin, Pin, LegacyPin, JObLevel, EmpName FROM employees, @StartPin 
		WHERE (ManagerPin = GivenPin AND LegacyPin != GivenPin);
		EXEC DrillEmployeesTableC @NextPin,0;
	END
	IF (@end = 1) BEGIN SELECT * FROM #Result; END
END
GO

DECLARE @StartPin AS PinType;
INSERT INTO @StartPin (GivenPin) Values ('123456');
EXEC DrillEmployeesTableC @StartPin