# Bank Management System README

## Overview
The Bank Management System is a Java-based application designed to manage bank operations such as customer and employee management, account handling, transactions, and more. This README provides instructions on how to set up and use the project.

## Requirements
- **MySQL**: Ensure you have MySQL installed on your system.
- **Java Development Kit (JDK)**: Install JDK to compile and run Java programs.
- **Eclipse IDE**: Set up Eclipse IDE for Java development.

## Setup Instructions

### 1. Database Setup
Execute the following SQL scripts in your MySQL database:
- **bankManager.sql**: Creates the necessary tables for the bank management system.
- **procedures.sql**: Defines stored procedures for operations like deposit, withdrawal, transfer, etc.
- **dataEntries.sql (Optional)**: Inserts sample data entries into the tables for testing purposes.

### 2. Eclipse Setup
- Open Eclipse IDE.
- Create a new Java project.

### 3. Add MySQL Connector to Project
- Download the `mysql-connector-j-8.3.0.jar` file.
- In Eclipse, right-click on your project and select **Build Path** > **Configure Build Path**.
- Navigate to the **Libraries** tab and click **Add External JARs**.
- Select the `mysql-connector-j-8.3.0.jar` file and click **Open** to add it to your project's classpath.

## Usage

### 1. Bank Manager Login
- Run `BankManagerLogin.java` to launch the bank management system.
- Enter the login credentials to access the system:
  - **Username**: `Manager`
  - **Password**: `loginPass`

### 2. Bank Management System
Once logged in, you can perform various operations such as:
- Adding and managing customers and employees.
- Opening, closing, and updating accounts.
- Depositing, withdrawing, and transferring funds.
- Checking account balances and transaction history.
- Depositing interest into savings accounts.

### 3. Testing
- Use the provided sample data entries to test the system's different functionalities.
- Explore the menus and options to understand the workflow and features of the application.
