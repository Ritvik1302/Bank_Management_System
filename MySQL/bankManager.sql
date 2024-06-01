drop database bankmanager;
create database bankmanager;

use bankmanager;
-- CREATE USER sqluser IDENTIFIED BY 'sqluserpw';

grant usage on *.* to sqluser;
grant all privileges on bankmanager.* to sqluser;
ALTER USER 'sqluser' IDENTIFIED WITH mysql_native_password BY 'sqluserpw';
flush privileges;

CREATE TABLE Bank (
    Branch_ID INT PRIMARY KEY auto_increment,
    No_Of_Employees INT,
    City VARCHAR(255),
    Pincode VARCHAR(10),
    Address VARCHAR(255)
);

CREATE TABLE Employee (
    Employee_ID INT PRIMARY KEY auto_increment,
    Emp_Name VARCHAR(255),
    Gender CHAR(1),
    Position VARCHAR(255),
    Salary DECIMAL(10, 2),
    Date_of_Birth DATE,
    Branch_ID INT,
    FOREIGN KEY (Branch_ID) REFERENCES Bank(Branch_ID)
);

CREATE TABLE Employee_Phone (
    Employee_ID INT,
    Mobile_No VARCHAR(15),
    PRIMARY KEY (Mobile_No),
    FOREIGN KEY (Employee_ID) REFERENCES Employee(Employee_ID)
);

CREATE TABLE Employee_Email (
    Employee_ID INT,
    Email VARCHAR(255),
    PRIMARY KEY (Email),
    FOREIGN KEY (Employee_ID) REFERENCES Employee(Employee_ID)
);

CREATE TABLE Customer (
    Cust_ID INT PRIMARY KEY auto_increment,
    Cust_Name VARCHAR(255),
    Gender CHAR(1),
    Date_of_Birth DATE,
    Aadhar_No VARCHAR(12)
);

CREATE TABLE Customer_Phone (
    Cust_ID INT,
    Mobile_No VARCHAR(15),
    PRIMARY KEY (Mobile_No),
    FOREIGN KEY (Cust_ID) REFERENCES Customer(Cust_ID)
);

CREATE TABLE Customer_Email (
    Cust_ID INT,
    Email VARCHAR(255),
    PRIMARY KEY (Email),
    FOREIGN KEY (Cust_ID) REFERENCES Customer(Cust_ID)
);

CREATE TABLE Account (
    Acc_Id INT PRIMARY KEY auto_increment,
    Cust_ID INT,
    Type VARCHAR(10),
    Branch_ID INT,
    Balance DECIMAL(10, 2),
    Status VARCHAR(10),
    FOREIGN KEY (Cust_ID) REFERENCES Customer(Cust_ID),
    FOREIGN KEY (Branch_Id) REFERENCES Bank(Branch_Id)
);

CREATE TABLE Account_Deletion (
    Acc_Id INT,
    Deletion_Date DATE,
    PRIMARY KEY (Acc_Id),
    FOREIGN KEY (Acc_Id) REFERENCES Account(Acc_Id)
);

CREATE TABLE Account_Creation (
    Acc_Id INT,
    Emp_Id INT,
    Creation_Date DATE,
    PRIMARY KEY (Acc_Id),
    FOREIGN KEY (Acc_Id) REFERENCES Account(Acc_Id),
    FOREIGN KEY (Emp_Id) REFERENCES Employee(Employee_ID)
);

CREATE TABLE Transaction (
    Transaction_ID INT PRIMARY KEY auto_increment,
    Acc_ID INT,
    Type VARCHAR(10),
    Amount DECIMAL(10, 2),
    Date DATE,
    Emp_Id INT,
    FOREIGN KEY (Acc_ID) REFERENCES Account(Acc_Id),
    FOREIGN KEY (Emp_Id) REFERENCES Employee(Employee_ID)
);

CREATE TABLE Interest_Deposition (
    Acc_Id INT,
    Rate_of_Interest DECIMAL(5, 2),
    Date DATE,
    PRIMARY KEY (Acc_Id, Date),
    FOREIGN KEY (Acc_Id) REFERENCES Account(Acc_Id)
);

