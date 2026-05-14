CREATE TABLE Business_Credit(
    id INT IDENTITY PRIMARY KEY,
    amount REAL,
    interest REAL,
    total_to_pay REAL,
    date DATE,
    status VARCHAR(20)
)