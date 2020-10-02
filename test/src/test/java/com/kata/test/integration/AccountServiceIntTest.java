package com.kata.test.integration;

import com.kata.test.domain.Account;
import com.kata.test.domain.Operation;
import com.kata.test.exception.NoSuchAccountException;
import com.kata.test.repository.AccountRepository;
import com.kata.test.repository.OperationRepository;
import com.kata.test.service.AccountService;
import com.kata.test.service.OperationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class AccountServiceIntTest {

    /**
     * Spring Boot Test Configuration
     */
    @TestConfiguration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public AccountService accountService() {
            return new AccountService();
        }
    }

    @TestConfiguration
    static class OperationServiceTestContextConfiguration {

        @Bean
        public OperationService operationService() {
            return new OperationService();
        }
    }

    @Autowired
    private AccountService accountService;

    @Autowired
    private OperationService operationService;

    @MockBean
    private  AccountRepository accountRepository;

    @MockBean
    private OperationRepository operationRepository;

    private List<Operation> operations;
    private Account account;

    /**
     * Prerequisite
     */
    @Before
    public void setUp() throws NoSuchAccountException {
        account = new Account();
        account.setBalance(5000);
        account.setId(1L);
        Mockito.when(accountRepository.findById(account.getId()))
                .thenReturn(Optional.ofNullable(account));
        operationService.doDeposit(account.getId(), 3000L);
        operationService.doDeposit(account.getId(), 3000L);
        operationService.doWithdrawal(account.getId(), 3000L);
        operationService.doDeposit(account.getId(), 4000L);
        operationService.doWithdrawal(account.getId(), 1000L);
        operationService.doDeposit(account.getId(), 3000L);

    }

    /**
     *
     * Test listAllOperations when account isn't in database
     *
     * Getting NoSuchAccountException
     */
    @Test(expected = NoSuchAccountException.class)
    public void listAllOperations_should_throw_exception_for_no_such_account() throws Exception {
        accountService.listAllOperations(2L);
        Assert.fail("should have thrown NoSuchAccountException ");
    }

    /**
     *
     * Test listAllOperations when account is in database with operations
     *
     * Getting Account Statement
     */
    @Test
    public void listAllOperations_should_successfully_return_all_account_operations() throws NoSuchAccountException {
        List<Operation> operations = accountService.listAllOperations(account.getId());
        assert !(operations).isEmpty();

    }

}

