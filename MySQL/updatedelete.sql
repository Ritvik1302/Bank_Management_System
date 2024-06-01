update customer set Cust_Name= "Aryan" where cust_id = 900001;
update employee set Branch_Id = 101 where Employee_Id = 100001;
update account set Balance = 5000 where Acc_Id = 700001;

delete from customer where cust_id = 90001;
update account set status = "CLOSED" where Acc_Id = 700001;
insert into account_deletion values(700001, CURDATE());