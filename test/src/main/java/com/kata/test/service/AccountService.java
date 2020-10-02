package com.kata.test.service;

import com.kata.test.TestApplication;
import com.kata.test.domain.Account;
import com.kata.test.domain.Operation;
import com.kata.test.exception.NoSuchAccountException;
import com.kata.test.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@Transactional
public class AccountService {
    // Logger
    private static final Logger logger = LogManager.getLogger(TestApplication.class);

    @Autowired
    private  AccountRepository accountRepository;

    /**
     * User Story 3
     * @param accountId account identifier
     * @return all operations on a given account
     * @throws NoSuchAccountException
     */
    public List<Operation> listAllOperations(long accountId) throws NoSuchAccountException {
        logger.info("Getting Account with identifier...");
        Optional<Account> optionalBankAccount = accountRepository.findById(accountId);
        if(!optionalBankAccount.isPresent()){
            throw new NoSuchAccountException(": "+accountId);
        }
        logger.info("Getting the Account Operations");
        optionalBankAccount.get().getOperations().stream().forEach(System.out::println);
        return optionalBankAccount.get().operations;
    }

}







