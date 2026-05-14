CREATE TRIGGER trg_after_purchase
ON Purchase_of_raw_materials
AFTER INSERT
AS
BEGIN

    -- уменьшаем бюджет
    UPDATE Budget
    SET budget_amount = budget_amount - i.amount
    FROM inserted i


    -- увеличиваем склад сырья
   UPDATE r
    SET 
        r.quantity = r.quantity + i.quantity,
        r.amount = r.amount + i.amount
    FROM Raw_materials r
    JOIN inserted i
        ON r.ID = i.raw_materials;

END