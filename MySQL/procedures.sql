DELIMITER $$
CREATE PROCEDURE Deposit(
    IN account_id INT,
    IN transaction_amount DECIMAL(10, 2)
)
BEGIN
    DECLARE current_balance DECIMAL(10, 2);

    SELECT Balance INTO current_balance FROM Account WHERE Acc_Id = account_id;

	UPDATE Account SET Balance = current_balance + transaction_amount WHERE Acc_Id = account_id;
	INSERT INTO Transaction (Acc_ID, Type, Amount, Date)
	VALUES (account_id, 'Credit', transaction_amount, CURRENT_DATE);
	SELECT 'Transaction successful' AS Message;
    
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE WithdrawMoney(
    IN account_id INT,
    IN withdrawal_amount DECIMAL(10, 2)
)
BEGIN
    DECLARE current_balance DECIMAL(10, 2);

    SELECT Balance INTO current_balance FROM Account WHERE Acc_Id = account_id;

    IF current_balance >= withdrawal_amount THEN
        UPDATE Account SET Balance = current_balance - withdrawal_amount WHERE Acc_Id = account_id;

        INSERT INTO Transaction (Acc_ID, Type, Amount, Date)
        VALUES (account_id, 'Withdrawal', withdrawal_amount, CURRENT_DATE);

        SELECT 'Withdrawal successful' AS Message;
    ELSE
        SELECT 'Insufficient funds' AS Message;
    END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE TransferMoney(
    IN from_account_id INT,
    IN to_account_id INT,
    IN transfer_amount DECIMAL(10, 2)
)
BEGIN
    DECLARE from_balance DECIMAL(10, 2);
    DECLARE to_balance DECIMAL(10, 2);

    SELECT Balance INTO from_balance FROM Account WHERE Acc_Id = from_account_id;

    SELECT Balance INTO to_balance FROM Account WHERE Acc_Id = to_account_id;

    IF from_balance >= transfer_amount THEN
        UPDATE Account SET Balance = from_balance - transfer_amount WHERE Acc_Id = from_account_id;
        UPDATE Account SET Balance = to_balance + transfer_amount WHERE Acc_Id = to_account_id;

        INSERT INTO Transaction (Acc_ID, Type, Amount, Date)
        VALUES (from_account_id, 'Transfered', transfer_amount, CURRENT_DATE);

        INSERT INTO Transaction (Acc_ID, Type, Amount, Date)
        VALUES (to_account_id, 'Credited', transfer_amount, CURRENT_DATE);

        SELECT 'Transfer successful' AS Message;
    ELSE
        SELECT 'Insufficient funds' AS Message;
    END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE AddInterestToSavings(
    IN interest_rate DECIMAL(5, 2)
)
BEGIN
    DECLARE creation_date DATE;
    DECLARE days_since_creation INT;
    DECLARE interest_amount DECIMAL(10, 2);
    DECLARE acc_id INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur CURSOR FOR SELECT ac.Acc_Id, ac.Creation_Date FROM Account_Creation ac INNER JOIN Account a ON ac.Acc_Id = a.Acc_Id WHERE a.Type = 'Savings';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO acc_id, creation_date;
        IF done THEN
            LEAVE read_loop;
        END IF;

        SET days_since_creation = DATEDIFF(CURDATE(), creation_date);

        IF days_since_creation > 30 THEN
            SELECT (a.Balance * (interest_rate / 100)) INTO interest_amount FROM Account a WHERE a.Acc_Id = acc_id;

            UPDATE Account SET Balance = Balance + interest_amount WHERE Acc_Id = acc_id;

            INSERT INTO Interest_Deposition (Acc_Id, Rate_of_Interest, Date) VALUES (acc_id, interest_rate, current_date);
        END IF;
    END LOOP;
    CLOSE cur;
    
END$$

DELIMITER ;
