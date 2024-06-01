use bankmanager;

INSERT INTO Bank (Branch_ID, No_Of_Employees, City, Pincode, Address)
VALUES (100, 1, 'Mumbai', '400001', '123 Nariman Point');

INSERT INTO Bank (No_Of_Employees, City, Pincode, Address)
VALUES (1, 'Kapurthala', '144601', '123 Shalimar Bagh'),
       (0, 'Delhi', '110001', '456 Connaught Place');

INSERT INTO Employee (Employee_Id, Emp_Name, Gender, Position, Salary, Date_of_Birth, Branch_ID)
VALUES (100001,'Rajesh Kumar', 'M', 'Manager', 60000.00, '1980-05-15', 100);

INSERT INTO Employee (Emp_Name, Gender, Position, Salary, Date_of_Birth, Branch_ID)
VALUES ('Priya Sharma', 'F', 'Assistant', 45000.00, '1985-10-20', 101);

INSERT INTO Employee_Phone (Employee_ID, Mobile_No)
VALUES (100001, '9876543210'),
       (100002, '9998887776');

INSERT INTO Employee_Email (Employee_ID, Email)
VALUES (100001, 'rajesh.kumar@example.com'),
       (100002, 'priya.sharma@example.com');

INSERT INTO Customer (Cust_Id, Cust_Name, Gender, Date_of_Birth, Aadhar_No)
VALUES (900001, 'Amit Shah', 'M', '1978-08-10', '123456789012');

INSERT INTO Customer (Cust_Name, Gender, Date_of_Birth, Aadhar_No)
VALUES ('Neha Patel', 'F', '1983-03-25', '987654321098');

INSERT INTO Customer_Phone (Cust_ID, Mobile_No)
VALUES (900001, '9876543210'),
       (900002, '9998887776');

INSERT INTO Customer_Email (Cust_ID, Email)
VALUES (900001, 'amit.shah@example.com'),
       (900002, 'neha.patel@example.com');

INSERT INTO Account (Acc_Id,Cust_ID, Type, Branch_Id, Balance, Status)
VALUES (700001, 900001, 'Savings', 100, 2000.00, 'Open');

INSERT INTO Account (Cust_ID, Type, Branch_Id, Balance, Status)
VALUES (900002, 'Savings', 101, 1500.00, 'Open');

INSERT INTO Account_Creation (Acc_Id, Emp_Id, Creation_Date)
VALUES (700001, 100001, '2022-01-01'),
       (700002, 100002, '2022-02-01');

INSERT INTO Transaction (Acc_ID, Type, Amount, Date, Emp_Id)
VALUES (700001, 'Deposit', 2000.00, '2022-01-01', 100001),
       (700002, 'Deposit', 1500.00, '2022-02-01', 100002);

-- INSERT INTO Interest_Deposition (Acc_Id, Rate_of_Interest, Date)
-- VALUES (700001, 6.0, '2022-02-01'),
--        (700002, 5.5, '2022-03-01');

INSERT INTO Transaction (Transaction_ID, Acc_Id, Type, Amount,Date,Emp_Id)
VALUES (500001, 700001, 'Withdraw', 100, '2022-02-01', 100001);
