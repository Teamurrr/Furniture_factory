CREATE TABLE SalaryPayments (
    id INT IDENTITY PRIMARY KEY,
    employee_id INT,
    amount REAL,
    payment_date DATE,
    FOREIGN KEY (employee_id) REFERENCES Employees(id)
);