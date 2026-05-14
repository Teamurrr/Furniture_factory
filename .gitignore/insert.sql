INSERT INTO Units_of_measurement (name) VALUES
('piece'),
('kg'),
('liter'),
('meter');

INSERT INTO Positions (job_title) VALUES
('Carpenter'),
('Manager'),
('Sales Manager'),
('Warehouse Worker');

INSERT INTO Employees (full_name, position, salary, address, phone_number) VALUES
('Ivan Petrov', 1, 1200, 'Bishkek', '0700123456'),
('Anna Sidorova', 2, 1500, 'Bishkek', '0700111111'),
('Sergey Ivanov', 3, 1100, 'Bishkek', '0700222222'),
('Oleg Smirnov', 4, 900, 'Bishkek', '0700333333');

INSERT INTO Raw_materials (name, unit_of_measurement, quantity, amount) VALUES
('Wooden board', 4, 200, 4000),
('Screws', 1, 500, 500),
('Wood glue', 3, 50, 700),
('Varnish', 3, 30, 600),
('Metal legs', 1, 100, 2000);

INSERT INTO Finished_products (name, unit_of_measurement, quantity, amount) VALUES
('Chair', 1, 20, 3000),
('Table', 1, 10, 5000),
('Wardrobe', 1, 5, 7000),
('Shelf', 1, 15, 2500);

INSERT INTO Ingredients (products, raw_materials, quantity) VALUES
(1, 1, 2),
(1, 2, 10),
(1, 3, 0.1),

(2, 1, 5),
(2, 2, 20),
(2, 4, 0.2),

(3, 1, 10),
(3, 2, 40),
(3, 4, 0.5);

INSERT INTO Budget (budget_amount) VALUES
(50000);

INSERT INTO Purchase_of_raw_materials (raw_materials, quantity, amount, date, employee) VALUES
(1, 50, 1000, '2026-03-01', 4),
(2, 200, 200, '2026-03-02', 4),
(3, 20, 300, '2026-03-02', 4);


INSERT INTO Product_sales (products, quantity, amount, date, employee) VALUES
(1, 5, 1000, '2026-03-03', 3),
(2, 2, 1200, '2026-03-03', 3);

INSERT INTO Product_production (product, quantity, date, employee) VALUES
(1, 10, '2026-03-01', 1),
(2, 5, '2026-03-02', 1);

