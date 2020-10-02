CREATE TABLE ACCOUNT (
                         ACCOUNT_ID INT AUTO_INCREMENT  PRIMARY KEY,
                         BALANCE DOUBLE NOT NULL
);

CREATE TABLE OPERATION (
                         OPERATION_ID INT AUTO_INCREMENT  PRIMARY KEY,
                         DATE DATE NOT NULL,
                         TYPE VARCHAR NOT NULL,
                         AMOUNT INTEGER NOT NULL,
                         ACCOUNT_ID int not null
);

 ALTER TABLE OPERATION
    ADD CONSTRAINT OPERATION_ACCOUNT_ID_FK
        FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT;

