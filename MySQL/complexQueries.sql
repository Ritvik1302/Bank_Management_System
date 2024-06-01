Select * from account;

Call Deposit (700001, 5000);
Select * from account;

Call WithdrawMoney (700001, 1000);
Select * from account;

Call TransferMoney (700001, 700002, 2000);
Select * from account;

Call AddInterestToSavings (12);
Select * from account;
Select * from interest_deposition;
