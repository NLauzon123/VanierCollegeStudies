--output -1 = User doesn't exist.
--output 0 = Password incorrect for user.
--output 1 = Success.

CREATE PROCEDURE LoginUser
	@username VARCHAR(100),
    @password VARCHAR(100),
	@result INT OUTPUT
AS
BEGIN
	IF NOT EXISTS (SELECT 1 FROM Employees WHERE username = @username)
		BEGIN
			SET @result = -1;
			RETURN;
		END
	
    DECLARE @salt VARBINARY(64);
    DECLARE @hashedPassword VARBINARY(32);

	SELECT @hashedPassword = hashedPassword, @salt = salt
	FROM Employees
	WHERE username = @username

	IF @hashedPassword != HASHBYTES('SHA2_256', @salt + CAST(@password AS VARBINARY(100)))
		BEGIN
			SET @result = 0;
			RETURN;
		END

	SET @result = 1;
	RETURN
END

GO;

DECLARE @result INTEGER;

-- Expect result 1
EXEC LoginUser
    @username = 'lbouchard',
    @password = 'RTL12345678',
    @result = @result OUTPUT;
PRINT @result;

-- Expect result 1
EXEC LoginUser
    @username = 'artmorin',
    @password = 'REM12345678',
    @result = @result OUTPUT;
PRINT @result;

--Expect result 0
EXEC LoginUser
    @username = 'artmorin',
    @password = 'wrongpassword',
    @result = @result OUTPUT;
PRINT @result;

--Expect result -1
EXEC LoginUser
    @username = 'artrin',
    @password = 'REM12345678',
    @result = @result OUTPUT;
PRINT @result;