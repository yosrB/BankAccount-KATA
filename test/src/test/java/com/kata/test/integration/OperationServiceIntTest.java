package com.kata.test.integration;

import com.kata.test.domain.Account;
import com.kata.test.domain.Operation;
import com.kata.test.domain.OperationType;
import com.kata.test.exception.NoSuchAccountException;
import com.kata.test.repository.AccountRepository;
import com.kata.test.repository.OperationRepository;
import com.kata.test.service.AccountService;
import com.kata.test.service.OperationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OperationServiceIntTest {

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
    private AccountRepository accountRepository;

    @MockBean
    private OperationRepository operationRepository;

    private List<Operation> operations;
    private Account account;
    private  Operation operation;

    @Before
    public void setUp() throws NoSuchAccountException {
        account = new Account();
        account.setBalance(5000);
        account.setId(1L);
        when(accountRepository.findById(account.getId()))
                .thenReturn(Optional.ofNullable(account));
        operation = new Operation(new Date(), OperationType.DEPOSIT, 3000, 2000, account);

    }

    /**
     *
     * Test createAndPerformOperation when account isn't in database
     *
     * Getting NoSuchAccountException
     */
    @Test(expected = NoSuchAccountException.class)
    public void createAndPerformOperation_should_throw_NoSuchAccountException() throws NoSuchAccountException {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        operationService.createAndPerformOperation(12L,0,OperationType.WITHDRAWAL);
        Assert.fail("should have thrown NoSuchAccountException ");

    }

    /**
     * Test Deposit money should work
     */
    @Test
    public void createAndPerformOperation_should_perform_deposit() throws NoSuchAccountException {
        long currentAccountBalance = account.getBalance();
        Operation operation = operationService.createAndPerformOperation(1L,1000,OperationType.DEPOSIT);
        assert(operation.getType()).equals(OperationType.DEPOSIT);
        assert(operation.getAccount() != null);
        assert(operation.getAccount().getBalance() == currentAccountBalance+1000);
    }

    /**
     * Test withdrawal should work
     */
    @Test
    public void createAndPerformOperation_should_perform_withdrawal() throws NoSuchAccountException {
        long currentAccountBalance = account.getBalance();
        Operation operation = operationService.createAndPerformOperation(1L,1000,OperationType.WITHDRAWAL);
        assert(operation.getType()).equals(OperationType.WITHDRAWAL);
        assert(operation.getAccount() != null);
        assert(operation.getAccount().getBalance() == currentAccountBalance-1000);
    }

    /**
     * Test Deposit should perform and save operation
     */
    @Test
    public void doDeposit_should_perform_deposit_and_save_op() throws NoSuchAccountException {
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        long currentAccountBalance = account.getBalance();
        operationService.doDeposit(1L,1200L);
        assert (account.getOperations().get(0).getType()).equals(OperationType.DEPOSIT);
        assert (account.getOperations().get(0).getAmount()).equals(1200L);
    }

    /**
     * Test Withdrawal should perform and save operation
     */
    @Test
    public void doWithdrawal_should_perform_withdrawal_and_save_op() throws NoSuchAccountException {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        long currentAccountBalance = account.getBalance();
        operationService.doWithdrawal(1L,1200L);
        assert (account.getOperations().get(0).getType()).equals(OperationType.WITHDRAWAL);
        assert(account.getOperations().get(0).getAmount()).equals(-1200L);
    }
}


