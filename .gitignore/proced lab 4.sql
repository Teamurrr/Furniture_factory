--CREATE PROCEDURE CheckBudgetForSalary
--    @amount REAL,
--    @result INT OUTPUT
--AS
--BEGIN
--    DECLARE @budget REAL

--    SELECT @budget = budget_amount FROM Budget

--    IF @budget >= @amount
--        SET @result = 0
--    ELSE
--        SET @result = 1
----END

--ALTER PROCEDURE InsertSalaryPayment
--    @employee_id INT,
--    @amount REAL
--AS
--BEGIN
--    INSERT INTO SalaryPayments(employee_id, amount, payment_date)
--    VALUES (@employee_id, @amount, GETDATE())
--END

----CREATE PROCEDURE InsertSalaryPayment
--    @employee_id INT,
--    @amount REAL,
--    @payment_date DATE
--AS
--BEGIN
--    INSERT INTO SalaryPayments(employee_id, amount, payment_date)
--    VALUES (@employee_id, @amount, @payment_date)
--END