# BankAccount-KATA 

A SpringBoot application  

Overview: 

The application offers the possibility to have multiple accounts. For each account multiple operations. 

A bank account has a unique identifier. It's represented by a balance and a list of operations which could be a deposit or a withdrawal. 

An operation is categorised(deposit or a withdrawal), have an amount, an occurence date, a balance, an account and an unique ID. Each operation is linked to a specific account. 


Testing: 

The application is tested using Junit. 

Integration test for the services to test US1, US2 and US3 using LOG4j Logger.

Data creation : 

Migration script(flyway) allows the application to create two tables (Account and Operation). 
