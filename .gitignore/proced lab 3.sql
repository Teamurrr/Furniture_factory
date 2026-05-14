----CREATE PROCEDURE CheckProductQuantity
----    @product_id INT,
----    @quantity FLOAT,
----    @result INT OUTPUT
----AS
----BEGIN

----    DECLARE @available FLOAT

----    SELECT @available = quantity
----    FROM Finished_products
----    WHERE id = @product_id

----    IF @available >= @quantity
----        SET @result = 0
----    ELSE
----        SET @result = 1

----END


--CREATE PROCEDURE InsertProductSale
--    @product_id INT,
--    @quantity FLOAT,
--    @amount FLOAT,
--    @date DATE,
--    @employee_id INT
--AS
--BEGIN

--INSERT INTO Product_sales
--(products, quantity, amount, date, employee)

--VALUES
--(@product_id, @quantity, @amount, @date, @employee_id)

--END



