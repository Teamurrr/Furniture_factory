
-- Units of measurement
CREATE TABLE Units_of_measurement (
    ID INT PRIMARY KEY IDENTITY,
    name VARCHAR(50)
);


-- Positions
CREATE TABLE Positions (
    ID INT PRIMARY KEY IDENTITY,
    job_title VARCHAR(100)
);


-- Budget
CREATE TABLE Budget (
    ID INT PRIMARY KEY IDENTITY,
    budget_amount REAL
);


-- Raw materials
CREATE TABLE Raw_materials (
    ID INT PRIMARY KEY IDENTITY,
    name VARCHAR(100),
    unit_of_measurement INT,
    quantity REAL,
    amount REAL,

    FOREIGN KEY (unit_of_measurement)
    REFERENCES Units_of_measurement(ID)
    ON DELETE CASCADE
);


-- Finished products
CREATE TABLE Finished_products (
    ID INT PRIMARY KEY IDENTITY,
    name VARCHAR(100),
    unit_of_measurement INT,
    quantity REAL,
    amount REAL,

    FOREIGN KEY (unit_of_measurement)
    REFERENCES Units_of_measurement(ID)
    ON DELETE CASCADE
);


-- Employees
CREATE TABLE Employees (
    ID INT PRIMARY KEY IDENTITY,
    full_name VARCHAR(150),
    position INT,
    salary REAL,
    address VARCHAR(200),
    phone_number VARCHAR(50),

    FOREIGN KEY (position)
    REFERENCES Positions(ID)
    ON DELETE CASCADE
);


-- Ingredients
CREATE TABLE Ingredients (
    ID INT PRIMARY KEY IDENTITY,
    products INT,
    raw_materials INT,
    quantity REAL,

    FOREIGN KEY (products)
    REFERENCES Finished_products(ID)
    ON DELETE CASCADE,

    FOREIGN KEY (raw_materials)
    REFERENCES Raw_materials(ID)
    ON DELETE NO ACTION
);


-- Purchase of raw materials
CREATE TABLE Purchase_of_raw_materials (
    ID INT PRIMARY KEY IDENTITY,
    raw_materials INT,
    quantity REAL,
    amount REAL,
    date DATE,
    employee INT,

    FOREIGN KEY (raw_materials)
    REFERENCES Raw_materials(ID)
    ON DELETE NO ACTION,

    FOREIGN KEY (employee)
    REFERENCES Employees(ID)
    ON DELETE NO ACTION
);


-- Product sales
CREATE TABLE Product_sales (
    ID INT PRIMARY KEY IDENTITY,
    products INT,
    quantity REAL,
    amount REAL,
    date DATE,
    employee INT,

    FOREIGN KEY (products)
    REFERENCES Finished_products(ID)
    ON DELETE NO ACTION,

    FOREIGN KEY (employee)
    REFERENCES Employees(ID)
    ON DELETE NO ACTION
);


-- Product production
CREATE TABLE Product_production (
    ID INT PRIMARY KEY IDENTITY,
    product INT,
    quantity REAL,
    date DATE,
    employee INT,

    FOREIGN KEY (product)
    REFERENCES Finished_products(ID)
    ON DELETE NO ACTION,

    FOREIGN KEY (employee)
    REFERENCES Employees(ID)
    ON DELETE NO ACTION
);