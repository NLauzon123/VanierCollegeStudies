--output 0 = User doesn't exist.
--output 1 = Success.

CREATE PROCEDURE ResetPassword
    @username VARCHAR(100),
    @password VARCHAR(100),
	@result INT OUTPUT
AS
BEGIN

	IF NOT EXISTS (SELECT 1 FROM Employees WHERE username = @username)
		BEGIN
			SET @result = 0;
			RETURN;
		END

    DECLARE @salt VARBINARY(64) = CRYPT_GEN_RANDOM(64);
    DECLARE @hashedPassword VARBINARY(32);

    SET @hashedPassword = HASHBYTES('SHA2_256', @salt + CAST(@password AS VARBINARY(100)));

    UPDATE Employees 
	SET hashedPassword = @hashedPassword, salt = @salt 
	WHERE username = @username;

	SET @result = 1;
	RETURN
END;

GO;

DECLARE @result INTEGER;

-- Expect result 0
EXEC ResetPassword
    @username = 'nonExistantUser',
    @password = '34234234',
    @result = @result OUTPUT;
PRINT @result;

-- Expect result 1 (login user before changing password)
EXEC LoginUser
    @username = 'oliviaroy',
    @password = 'EXO12345678',
    @result = @result OUTPUT;
PRINT @result;

-- Expect result 1 (changing password)
EXEC ResetPassword
    @username = 'oliviaroy',
    @password = 'EXO87654321',
    @result = @result OUTPUT;
PRINT @result;

-- Expect result 0 (login user with old password)
EXEC LoginUser
    @username = 'oliviaroy',
    @password = 'EXO12345678',
    @result = @result OUTPUT;
PRINT @result;

-- Expect result 1 (login user with new password)
EXEC LoginUser
    @username = 'oliviaroy',
    @password = 'EXO87654321',
    @result = @result OUTPUT;
PRINT @result;