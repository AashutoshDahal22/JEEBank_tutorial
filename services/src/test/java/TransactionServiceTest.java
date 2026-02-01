import exception.InSufficientBalanceException;
import exception.InvalidAmountException;
import exception.InvalidUserException;
import interfaces.TransactionsRepositoryInterface;
import models.TransactionsModel;
import service.TransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //enable mocks for JUnit Mock and InjectMock won't work without this
public class TransactionServiceTest {
    @InjectMocks //creates instance to inject the mock data
    private TransactionsService transactionsService;

    //this one is mybatis mapper
    //private TransactionsMapper transactionsMapper;

    @Mock //creates mock object of this mapper to use in instantiated service
    private TransactionsRepositoryInterface transactionsRepository;

    private TransactionsModel transactionsModel;

    @BeforeEach
    void setUp() {
        transactionsModel = new TransactionsModel();
        transactionsModel.setUserId(1L);
        transactionsModel.setTargetAccountUserId(2L);
        transactionsModel.setAmount(1000.0);
    }

    @Test
    void successful_balance_check() {
        double expectedBalance = 2000.0;

        when(transactionsService.checkBalance(transactionsModel.getUserId())).thenReturn(expectedBalance);

        Double actualBalance = transactionsService.checkBalance(transactionsModel.getUserId());

        assertEquals(expectedBalance, actualBalance);
        verify(transactionsRepository).fetchBalance(transactionsModel.getUserId());
    }

    @Test
    void balance_check_without_user() {
        assertThrows(InvalidUserException.class, () -> transactionsService.checkBalance(null));
        verify(transactionsRepository, never()).fetchBalance(any());
    }

    @Test
    void negative_balance_check() { //checks negative balance of a users and returns it
        when(transactionsService.checkBalance(transactionsModel.getUserId())).thenReturn(-100.0);

        Double balance = transactionsService.checkBalance(transactionsModel.getUserId());

        assertEquals(-100.0, balance);
        verify(transactionsRepository).fetchBalance(transactionsModel.getUserId());
    }

    //withDraw
    @Test
    void check_successful_withdraw() {
        transactionsModel.setAmount(299.9);

        when(transactionsService.checkBalance(transactionsModel.getUserId())).thenReturn(500.0);

        transactionsService.withdraw(transactionsModel);

        verify(transactionsRepository).withdraw(transactionsModel);
    }

    @Test
    void no_balance_for_withdrawal() {
        transactionsModel.setAmount(1000.0);

        when(transactionsService.checkBalance(transactionsModel.getUserId())).thenReturn(100.0); //stubbing a method - returns fake value when this is called

        assertThrows(InSufficientBalanceException.class, () -> transactionsService.withdraw(transactionsModel));

        verify(transactionsRepository, never()).withdraw(any()); //verifies if this method is called or not // never(),times(),atleastOnce()
    }

    @Test
    void withdraw_negative_amount() {
        transactionsModel.setAmount(-1000.0);

        when(transactionsService.checkBalance(transactionsModel.getUserId())).thenReturn(100.0);

        assertThrows(InvalidAmountException.class, () -> transactionsService.withdraw(transactionsModel)); //.class represents the RuntimeException class itself and not the instance(object) of the class, although it is an instance of java.lang.class

        verify(transactionsRepository, never()).withdraw(any());
    }

    @Test
    void withdraw_null_amount() {
        transactionsModel.setAmount(null);

        assertThrows(InvalidAmountException.class, () -> transactionsService.deposit(transactionsModel));

        verify(transactionsRepository, never()).withdraw(any());
    }

    @Test
    void successful_deposit() {
        transactionsModel.setAmount(100.0);

        transactionsService.deposit(transactionsModel);

        verify(transactionsRepository).deposit(transactionsModel);
    }

    @Test
    void negative_value_deposit() {
        transactionsModel.setAmount(-100.0);

        assertThrows(InvalidAmountException.class, () -> transactionsService.deposit(transactionsModel));

        verify(transactionsRepository, never()).withdraw(any());
    }

    @Test
    void null_value_deposit() {
        transactionsModel.setAmount(null);

        assertThrows(InvalidAmountException.class, () -> transactionsService.deposit(transactionsModel));

        verify(transactionsRepository, never()).withdraw(any());
    }

    @Test
    void successful_transfer() {
        transactionsModel.setAmount(500.0);

        when(transactionsService.checkBalance(1L)).thenReturn(2000.0);

        transactionsService.transfer(transactionsModel);

        verify(transactionsRepository).fetchBalance(1L);
        verify(transactionsRepository).withdraw(argThat(t -> t.getUserId().equals(1L) && t.getAmount() == 500.0)); //argThat is an argument matcher which verifies the method based on the condition
        verify(transactionsRepository).depositTo(argThat(t -> t.getTargetAccountUserId().equals(2L) && t.getAmount() == 500.0));
    }

    @Test
    void transfer_from_and_to_same_userid() {
        transactionsModel.setUserId(1L);
        transactionsModel.setTargetAccountUserId(1L);

        when(transactionsService.checkBalance(1L)).thenReturn(1000.0);

        assertThrows(IllegalArgumentException.class, () -> transactionsService.transfer(transactionsModel));
        verify(transactionsRepository, never()).withdraw(any());
        verify(transactionsRepository, never()).depositTo(any());

    }

    @Test
    void transfer_fail_if_no_sufficient_balance() {
        transactionsModel.setUserId(1L);
        transactionsModel.setTargetAccountUserId(2L);
        transactionsModel.setAmount(1000.0);

        when(transactionsService.checkBalance(1L)).thenReturn(500.0);

        assertThrows(InSufficientBalanceException.class, () -> transactionsService.transfer(transactionsModel));

        verify(transactionsRepository).fetchBalance(1L);
        verify(transactionsRepository, never()).withdraw(any());
        verify(transactionsRepository, never()).depositTo(any());
    }
}
