CREATE TRIGGER trg_after_production
ON Product_production
AFTER INSERT
AS
BEGIN

-- уменьшение сырья
UPDATE r
SET r.quantity = r.quantity - (i.quantity * ins.Quantity)
FROM Raw_materials r
JOIN Ingredients i ON r.ID = i.raw_materials
JOIN inserted ins ON ins.Product = i.products

-- увеличение готовой продукции
UPDATE f
SET f.quantity = f.quantity + ins.Quantity
FROM Finished_products f
JOIN inserted ins ON f.ID = ins.Product

END