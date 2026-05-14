--CREATE PROCEDURE check_rawmaterials
--    @product_id INT,
--    @quantity FLOAT,
--    @result INT OUTPUT
--AS
--BEGIN

--IF EXISTS(
--SELECT *
--FROM Ingredients i
--JOIN Raw_materials r ON r.ID = i.raw_materials
--WHERE i.products = @product_id
--AND r.quantity < i.quantity * @quantity
--)
--SET @result = 1
--ELSE
--SET @result = 0

--END

--CREATE PROCEDURE add_production
--    @product INT,
--    @quantity FLOAT,
--    @date DATE,
--    @employee INT
--AS
--BEGIN

--INSERT INTO Product_production
--(Product, Quantity, Date, Employee)
--VALUES
--(@product, @quantity, @date, @employee)

--END