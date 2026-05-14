CREATE TRIGGER Trigger_Product_Sale
ON Product_sales
AFTER INSERT
AS
BEGIN

-- уменьшаем количество продукта на складе
UPDATE fp
SET fp.quantity = fp.quantity - i.quantity
FROM Finished_products fp
JOIN inserted i ON fp.ID = i.products

-- увеличиваем бюджет
UPDATE Budget
SET budget_amount = budget_amount + (SELECT SUM(i.amount) FROM inserted i)

END