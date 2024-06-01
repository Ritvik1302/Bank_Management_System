import javax.swing.*;

import java.sql.Statement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class MyFrame extends JFrame {

	private Connection connection;

	public MyFrame() {
		createGUI();
		initializeDatabase();
	}

	private void createGUI() {
		setTitle("Bank Manager Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

		JButton addEmployeeButton = new JButton("Add New Employee");
		JButton fetchEmployeeDetailsButton = new JButton("Fetch Employee Details");
		JButton openAccountButton = new JButton("Open Account for Customer");
		JButton balanceInquiryButton = new JButton("Check Account Balance");
		JButton depositButton = new JButton("Deposit into Customer Account");
		JButton withdrawalButton = new JButton("Withdrawal from Customer Account");
		JButton transactionSearchButton = new JButton("Search Transaction History");
		JButton interestDepositButton = new JButton("Deposit Interest");
		JButton updateAccountButton = new JButton("Update Account Details");
		JButton closeAccountButton = new JButton("Close Account");
		JButton addCustomerButton = new JButton("Add New Customer");
		JButton transferMoneyButton = new JButton("Transfer Money");

		addEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEmployee();
			}
		});
		
		addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
		fetchEmployeeDetailsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fetchEmployeeDetails();
			}
		});

		openAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAccount();
			}
		});

		balanceInquiryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkAccountBalance();
			}
		});

		depositButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				depositIntoAccount();
			}
		});

		withdrawalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				withdrawFromAccount();
			}
		});

		transactionSearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchTransactionHistory();
			}
		});

		interestDepositButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				depositInterest();
			}
		});

		updateAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccount();
			}
		});

		closeAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeAccount();
			}
		});
		transferMoneyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transferMoney();
			}
		});

		panel.add(addEmployeeButton);
		panel.add(fetchEmployeeDetailsButton);
		panel.add(openAccountButton);
		panel.add(balanceInquiryButton);
		panel.add(depositButton);
		panel.add(withdrawalButton);
		panel.add(transactionSearchButton);
		panel.add(interestDepositButton);
		panel.add(updateAccountButton);
		panel.add(closeAccountButton);
		panel.add(addCustomerButton);
		panel.add(transferMoneyButton);
		add(panel);
		setVisible(true);
	}

	private void initializeDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/bankmanager";
			String username = "sqluser";
			String password = "sqluserpw";
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to connect to the database", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addEmployee() {
		try {
			String empName = JOptionPane.showInputDialog(null, "Enter Employee Name:");
			String gender = JOptionPane.showInputDialog(null, "Enter Gender (M/F):");
			String position = JOptionPane.showInputDialog(null, "Enter Position:");
			double salary = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Salary:"));
			String dob = JOptionPane.showInputDialog(null, "Enter Date of Birth (YYYY-MM-DD):");
			int branchID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Branch ID:"));
	
			String empSql = "INSERT INTO Employee (Emp_Name, Gender, Position, Salary, Date_of_Birth, Branch_ID) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement empStatement = connection.prepareStatement(empSql, Statement.RETURN_GENERATED_KEYS);
			empStatement.setString(1, empName);
			empStatement.setString(2, gender);
			empStatement.setString(3, position);
			empStatement.setDouble(4, salary);
			empStatement.setDate(5, java.sql.Date.valueOf(dob));
			empStatement.setInt(6, branchID);
			empStatement.executeUpdate();
	
			ResultSet generatedKeys = empStatement.getGeneratedKeys();
			int empID = -1;
			if (generatedKeys.next()) {
				empID = generatedKeys.getInt(1);
			}
	
			String mobileNoInput = JOptionPane.showInputDialog(null, "Enter Mobile Numbers (comma-separated):");
			String[] mobileNos = mobileNoInput.split(",");
			for (String mobileNo : mobileNos) {
				String mobileSQL = "INSERT INTO Employee_Phone (Employee_ID, Mobile_No) VALUES (?, ?)";
				PreparedStatement mobileStatement = connection.prepareStatement(mobileSQL);
				mobileStatement.setInt(1, empID);
				mobileStatement.setString(2, mobileNo.trim());
				mobileStatement.executeUpdate();
			}
	
			String emailInput = JOptionPane.showInputDialog(null, "Enter Email Addresses (comma-separated):");
			String[] emails = emailInput.split(",");
			for (String email : emails) {
				String emailSQL = "INSERT INTO Employee_Email (Employee_ID, Email) VALUES (?, ?)";
				PreparedStatement emailStatement = connection.prepareStatement(emailSQL);
				emailStatement.setInt(1, empID);
				emailStatement.setString(2, email.trim());
				emailStatement.executeUpdate();
			}
	
			String bankSql = "UPDATE Bank SET No_Of_Employees = No_Of_Employees + 1 WHERE Branch_ID = ?";
			PreparedStatement bankStatement = connection.prepareStatement(bankSql);
			bankStatement.setInt(1, branchID);
			bankStatement.executeUpdate();
	
			JOptionPane.showMessageDialog(null, "Employee added successfully", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to add employee", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void fetchEmployeeDetails() {
		try {
			int employeeID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Employee ID:"));

			String sql = "SELECT * FROM Employee WHERE Employee_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeeID);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String empName = resultSet.getString("Emp_Name");
				String gender = resultSet.getString("Gender");
				String position = resultSet.getString("Position");
				double salary = resultSet.getDouble("Salary");
				String dob = resultSet.getString("Date_of_Birth");
				int branchID = resultSet.getInt("Branch_ID");

				String message = "Employee ID: " + employeeID + "\nEmployee Name: " + empName + "\nGender: " + gender
						+ "\nPosition: " + position + "\nSalary: " + salary + "\nDate of Birth: " + dob
						+ "\nBranch ID: " + branchID;

				JOptionPane.showMessageDialog(null, message, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to fetch employee details", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openAccount() {
	    try {
	        int custID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Customer ID:"));
	        String accountType = JOptionPane.showInputDialog(null, "Enter Account Type (Savings/Current):");
	        String branch = JOptionPane.showInputDialog(null, "Enter Branch:");
	        double balance = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Initial Balance:"));
	        
	        int empID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Employee ID:"));

	        String status = "Open";

	        String accountSQL = "INSERT INTO Account (Cust_ID, Type, Branch_Id, Balance, Status) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement accountStatement = connection.prepareStatement(accountSQL, Statement.RETURN_GENERATED_KEYS);
	        accountStatement.setInt(1, custID);
	        accountStatement.setString(2, accountType);
	        accountStatement.setString(3, branch);
	        accountStatement.setDouble(4, balance);
	        accountStatement.setString(5, status);
	        accountStatement.executeUpdate();
	        
	        ResultSet generatedKeys = accountStatement.getGeneratedKeys();
	        int accID = -1;
	        if (generatedKeys.next()) {
	            accID = generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Failed to get auto-generated Acc_Id value.");
	        }
	        
	        String accountCreationSQL = "INSERT INTO Account_Creation (Acc_Id, Emp_Id, Creation_Date) VALUES (?, ?, CURDATE())";
	        PreparedStatement accountCreationStatement = connection.prepareStatement(accountCreationSQL);
	        accountCreationStatement.setInt(1, accID);
	        accountCreationStatement.setInt(2, empID);
	        accountCreationStatement.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Account opened successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to open account", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void transferMoney() {
	    try {
	        int fromAccountID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter From Account ID:"));
	        int toAccountID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter To Account ID:"));
	        double transferAmount = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Transfer Amount:"));

	        String sql = "CALL TransferMoney(?, ?, ?)";
	        PreparedStatement statement = connection.prepareCall(sql);
	        statement.setInt(1, fromAccountID);
	        statement.setInt(2, toAccountID);
	        statement.setDouble(3, transferAmount);
	        boolean hasResults = statement.execute();

	        if (hasResults) {
	            ResultSet rs = statement.getResultSet();
	            while (rs.next()) {
	                String message = rs.getString("Message");
	                JOptionPane.showMessageDialog(null, message, "Transfer Status", JOptionPane.INFORMATION_MESSAGE);
	            }
	        }
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to transfer money", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void checkAccountBalance() {
		try {
			int accID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Account ID:"));

			String sql = "SELECT Balance FROM Account WHERE Acc_Id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, accID);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				double balance = resultSet.getDouble("Balance");
				JOptionPane.showMessageDialog(null, "Account Balance: " + balance, "Account Balance",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to check account balance", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void depositIntoAccount() {
	    try {
	        int accID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Account ID:"));
	        double amount = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Deposit Amount:"));

	        PreparedStatement callableStatement = connection.prepareCall("{call Deposit(?, ?)}");
	        callableStatement.setInt(1, accID);
	        callableStatement.setDouble(2, amount);
	        callableStatement.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Deposit successful", "Success", JOptionPane.INFORMATION_MESSAGE);
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to deposit", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void withdrawFromAccount() {
	    try {
	        int accID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Account ID:"));
	        double amount = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Withdrawal Amount:"));

	        PreparedStatement callableStatement = connection.prepareCall("{call WithdrawMoney(?, ?)}");
	        callableStatement.setInt(1, accID);
	        callableStatement.setDouble(2, amount);
	        callableStatement.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Withdrawal successful", "Success", JOptionPane.INFORMATION_MESSAGE);
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to withdraw", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void searchTransactionHistory() {
		try {
			int accID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Account ID:"));
			String searchCriteria = JOptionPane.showInputDialog(null, "Enter Search Criteria (Date/Amount/Type):");

			String sql = "SELECT * FROM Transaction WHERE Acc_ID = ?";
			switch (searchCriteria.toLowerCase()) {
			case "date":
				sql += " ORDER BY Date DESC";
				break;
			case "amount":
				sql += " ORDER BY Amount DESC";
				break;
			case "type":
				sql += " ORDER BY Type";
				break;
			default:
				JOptionPane.showMessageDialog(null, "Invalid search criteria", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, accID);
			ResultSet resultSet = statement.executeQuery();

			StringBuilder history = new StringBuilder("Transaction History:\n");
			while (resultSet.next()) {
				int transactionID = resultSet.getInt("Transaction_ID");
				String type = resultSet.getString("Type");
				double amount = resultSet.getDouble("Amount");
				String date = resultSet.getString("Date");
				history.append("Transaction ID: ").append(transactionID).append(", Type: ").append(type)
						.append(", Amount: ").append(amount).append(", Date: ").append(date).append("\n");
			}
			JOptionPane.showMessageDialog(null, history.toString(), "Transaction History",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to fetch transaction history", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void depositInterest() {
	    try {
	        double interestRate = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Interest Rate:"));

	        String sql = "{CALL AddInterestToSavings(?)}";
	        PreparedStatement statement = connection.prepareCall(sql);
	        statement.setDouble(1, interestRate);
	        statement.execute();

	        JOptionPane.showMessageDialog(null, "Interest deposited successfully", "Success",
	                JOptionPane.INFORMATION_MESSAGE);
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to deposit interest", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void updateAccount() {
		try {
			int accID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Account ID:"));
			String branch = JOptionPane.showInputDialog(null, "Enter New Branch:");
			String status = JOptionPane.showInputDialog(null, "Enter New Status (Open/Closed):");

			String sql = "UPDATE Account SET Branch_Id = ?, Status = ? WHERE Acc_Id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, branch);
			statement.setString(2, status);
			statement.setInt(3, accID);
			int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated > 0) {
				JOptionPane.showMessageDialog(null, "Account updated successfully", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to update account", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to update account", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void closeAccount() {
	    try {
	        int accID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Account ID:"));
	        
	        String status = "Closed";

	        String updateSQL = "UPDATE Account SET Status = ? WHERE Acc_Id = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
	        updateStatement.setString(1, status);
	        updateStatement.setInt(2, accID);
	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	            String insertionSQL = "INSERT INTO Account_Deletion (Acc_Id, Deletion_Date) VALUES (?, CURDATE())";
	            PreparedStatement insertionStatement = connection.prepareStatement(insertionSQL);
	            insertionStatement.setInt(1, accID);
	            insertionStatement.executeUpdate();

	            JOptionPane.showMessageDialog(null, "Account closed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(null, "Failed to close account", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to close account", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void addCustomer() {
		try {
			String custName = JOptionPane.showInputDialog(null, "Enter Customer Name:");
			String gender = JOptionPane.showInputDialog(null, "Enter Gender (M/F):");
			String dob = JOptionPane.showInputDialog(null, "Enter Date of Birth (YYYY-MM-DD):");
			String aadharNo = JOptionPane.showInputDialog(null, "Enter Aadhar Number:");
	
			String customerSQL = "INSERT INTO Customer (Cust_Name, Gender, Date_of_Birth, Aadhar_No) VALUES (?, ?, ?, ?)";
			PreparedStatement customerStatement = connection.prepareStatement(customerSQL, Statement.RETURN_GENERATED_KEYS);
			customerStatement.setString(1, custName);
			customerStatement.setString(2, gender);
			customerStatement.setDate(3, java.sql.Date.valueOf(dob));
			customerStatement.setString(4, aadharNo);
			customerStatement.executeUpdate();
	
			ResultSet generatedKeys = customerStatement.getGeneratedKeys();
			int custID = -1;
			if (generatedKeys.next()) {
				custID = generatedKeys.getInt(1);
			}
	
			String mobileNoInput = JOptionPane.showInputDialog(null, "Enter Mobile Numbers (comma-separated):");
			String[] mobileNos = mobileNoInput.split(",");
			
			for (String mobileNo : mobileNos) {
				String mobileSQL = "INSERT INTO Customer_Phone (Cust_ID, Mobile_No) VALUES (?, ?)";
				PreparedStatement mobileStatement = connection.prepareStatement(mobileSQL);
				mobileStatement.setInt(1, custID);
				mobileStatement.setString(2, mobileNo.trim());
				mobileStatement.executeUpdate();
			}
	
			String emailInput = JOptionPane.showInputDialog(null, "Enter Email Addresses (comma-separated):");
			String[] emails = emailInput.split(",");
			for (String email : emails) {
				String emailSQL = "INSERT INTO Customer_Email (Cust_ID, Email) VALUES (?, ?)";
				PreparedStatement emailStatement = connection.prepareStatement(emailSQL);
				emailStatement.setInt(1, custID);
				emailStatement.setString(2, email.trim());
				emailStatement.executeUpdate();
			}
	
			JOptionPane.showMessageDialog(null, "Customer added successfully", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to add customer", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MyFrame();
			}
		});
	}
}