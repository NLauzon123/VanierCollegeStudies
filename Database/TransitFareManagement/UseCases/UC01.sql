--output 0 = User already exists.
--output 1 = Success.

CREATE PROCEDURE SignupUser
	@agencyID INTEGER,
	@firstName VARCHAR(100),
	@lastName VARCHAR(100),
    @username VARCHAR(100),
    @password VARCHAR(100),
	@result INT OUTPUT
AS
BEGIN

	IF EXISTS (SELECT 1 FROM Employees WHERE username = @username)
		BEGIN
			SET @result = 0;
			RETURN;
		END

    DECLARE @salt VARBINARY(64) = CRYPT_GEN_RANDOM(64);
    DECLARE @hashedPassword VARBINARY(32);

    SET @hashedPassword = HASHBYTES('SHA2_256', @salt + CAST(@password AS VARBINARY(100)));

    INSERT INTO Employees (agencyID, firstName, lastName, username, hashedPassword, salt) 
	VALUES(
	@agencyID, @firstName, @lastName, @username, @hashedPassword, @salt)

	SET @result = 1;
	RETURN
END;
GO;

-- Execution of the procedure to enter the employees in the Employees table
DECLARE @result INT;

EXEC SignupUser
    @agencyID = 1,
    @firstName = 'Emma',
    @lastName = 'Tremblay',
    @username = 'etremblay',
    @password = 'ARTM12345678',
    @result = @result OUTPUT;
PRINT @result;

EXEC SignupUser
    @agencyID = 2,
    @firstName = 'Thomas',
    @lastName = 'Gagnon',
    @username = 'tgagnon',
    @password = 'STM12345678',
    @result = @result OUTPUT;
PRINT @result;

EXEC SignupUser
    @agencyID = 3,
    @firstName = 'Olivia',
    @lastName = 'Roy',
    @username = 'oliviaroy',
    @password = 'EXO12345678',
    @result = @result OUTPUT;
PRINT @result;

EXEC SignupUser
    @agencyID = 4,
    @firstName = 'Louis',
    @lastName = 'Bouchard',
    @username = 'lbouchard',
    @password = 'RTL12345678',
    @result = @result OUTPUT;
PRINT @result;

EXEC SignupUser
    @agencyID = 5,
    @firstName = 'Charlotte',
    @lastName = 'Gauthier',
    @username = 'cgauthier',
    @password = 'STL12345678',
    @result = @result OUTPUT;
PRINT @result;

EXEC SignupUser
    @agencyID = 6,
    @firstName = 'Arthur',
    @lastName = 'Morin',
    @username = 'artmorin',
    @password = 'REM12345678',
    @result = @result OUTPUT;
PRINT @result;

--Try to signup existing user to expect code -0

EXEC SignupUser
    @agencyID = 6,
    @firstName = 'Arthur',
    @lastName = 'Morin',
    @username = 'artmorin',
    @password = 'REM12345678',
    @result = @result OUTPUT;
PRINT @result;
