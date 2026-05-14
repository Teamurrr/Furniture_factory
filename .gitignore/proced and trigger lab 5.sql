----CREATE PROCEDURE take_business_credit
----    @amount REAL
----AS
----BEGIN

----DECLARE @interest REAL
----DECLARE @total REAL

----IF @amount <= 50000
----    SET @interest = 10
----ELSE
----    SET @interest = 15

----SET @total = @amount + (@amount * @interest / 100)

----INSERT INTO Business_Credit(amount, interest, total_to_pay, date, status)
----VALUES(@amount, @interest, @total, GETDATE(), 'ACTIVE')

----END

--CREATE TRIGGER credit_budget_trigger
--ON Business_Credit
--AFTER INSERT
--AS
--BEGIN

--UPDATE Budget
--SET budget_amount = budget_amount + i.amount
--FROM inserted i

--END

--EXEC take_business_credit 40000