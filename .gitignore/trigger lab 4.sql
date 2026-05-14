CREATE TRIGGER trg_salary_payment
ON SalaryPayments
AFTER INSERT
AS
BEGIN
    DECLARE @amount REAL

    SELECT @amount = amount FROM inserted

    UPDATE Budget
    SET budget_amount = budget_amount - @amount
END