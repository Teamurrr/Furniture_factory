ALTER PROCEDURE take_business_credit
    @amount REAL,
    @result INT OUTPUT
AS
BEGIN

IF EXISTS (
    SELECT 1 
    FROM Business_Credit 
    WHERE status = 'ACTIVE'
)
BEGIN
    SET @result = 1 -- уже есть кредит
    RETURN
END

DECLARE @interest REAL
DECLARE @total REAL

IF @amount <= 50000
    SET @interest = 10
ELSE
    SET @interest = 15

SET @total = @amount + (@amount * @interest / 100)

INSERT INTO Business_Credit(amount, interest, total_to_pay, date, status)
VALUES(@amount, @interest, @total, GETDATE(), 'ACTIVE')

SET @result = 0 -- успех

END 