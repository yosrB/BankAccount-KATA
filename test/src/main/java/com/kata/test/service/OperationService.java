package com.kata.test.service;

import com.kata.test.TestApplication;
import com.kata.test.domain.Account;
import com.kata.test.domain.Operation;
import com.kata.test.domain.OperationType;
import com.kata.test.repository.AccountRepository;
import com.kata.test.repository.OperationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kata.test.exception.NoSuchAccountException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class OperationService {

    // Logger
    private static final Logger logger = LogManager.getLogger(TestApplication.class);

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * User Story 1
     * deposit the specified amount into the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public Account doDeposit(long accountId, long amount) throws NoSuchAccountException {
        logger.info("US1 : Doing a deposit on the account");
        Operation operation = createAndPerformOperation(accountId,amount,OperationType.DEPOSIT);
        Account account = accountRepository.findById(accountId).get();
        account.getOperations().add(operation);
        return account;
    }

    /**
     * User Story 2
     * debits the specified amount on the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public Account doWithdrawal(long accountId, long amount) throws NoSuchAccountException {
        logger.info("US2 : Doing a withdrawal on the account");
        Operation operation = createAndPerformOperation(accountId,amount, OperationType.WITHDRAWAL);
        Account account = accountRepository.findById(accountId).get();
        account.getOperations().add(operation);
        return account;
    }

    /**
     * US 1 & 2
     * create and perform the specified operation on the given account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @param operationType the transaction type(debit or credit)
     * @return newly created operation
     * @throws NoSuchAccountException
     */
   public Operation createAndPerformOperation(long accountId, long amount, OperationType operationType) throws NoSuchAccountException {
       logger.info("create and perform the specified operation on the given account");
       Optional<Account> optionalBankAccount = accountRepository.findById(accountId);
        if(!optionalBankAccount.isPresent()){
            throw new NoSuchAccountException(": "+accountId);
        }
        Account account = optionalBankAccount.get();
        int opType = operationType.equals(OperationType.WITHDRAWAL ) ? -1 : 1;
        Operation operation = new Operation(new Date(), operationType, opType*amount, account.balance+opType*amount, account );
        account.balance+=opType*amount;
        operationRepository.save(operation);
        return operation;
    }
}
