--CREATE PROCEDURE CheckBudget
--    @amount REAL,
--    @result INT OUTPUT
--AS
--BEGIN

--    DECLARE @current_budget REAL

--    SELECT @current_budget = budget_amount
--    FROM Budget

--    IF @current_budget >= @amount
--        SET @result = 0
--    ELSE
--        SET @result = 1

--END


--CREATE PROCEDURE InsertPurchase
--    @raw_material_id INT,
--    @quantity REAL,
--    @amount REAL,
--    @date DATE,
--    @employee_id INT
--AS
--BEGIN

--    INSERT INTO Purchase_of_raw_materials
--    (raw_materials, quantity, amount, date, employee)
--    VALUES
--    (@raw_material_id, @quantity, @amount, @date, @employee_id)

--END