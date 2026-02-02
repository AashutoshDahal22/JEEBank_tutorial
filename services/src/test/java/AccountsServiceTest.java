import DTO.AccountsDTO;
import DTO.UsersDTO;
import accounts.AccountsStatus;
import exception.InvalidDataException;
import interfaces.accounts.AccountsRepositoryInterface;
import interfaces.users.UsersRepositoryInterface;
import models.AccountsModel;
import models.UsersModel;
import org.mockito.ArgumentCaptor;
import service.AccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountsServiceTest {

    @Mock
    private AccountsRepositoryInterface accountsRepository;

    @Mock
    private UsersRepositoryInterface usersRepository;

    @InjectMocks
    private AccountsService accountsService;

    private AccountsDTO accountsDTO;
    private UsersDTO usersDTO;

    @BeforeEach
        //runs before each test
    void setUp() {
        accountsDTO = new AccountsDTO();
        usersDTO = new UsersDTO();

        usersDTO.setId(1L);
        usersDTO.setName("Madara Uchiha");
        usersDTO.setEmail("random@gmail.com");
        usersDTO.setAddress("chandol");
        usersDTO.setPhoneNumber("9803133855");
        usersDTO.setBirthdate(LocalDate.of(2002, 1, 1));

        accountsDTO.setAccountNumber("12345");
        accountsDTO.setBalance(1000.0);
        accountsDTO.setAccountType("Savings");
        accountsDTO.setUserId(usersDTO.getId());
        accountsDTO.setStatus(AccountsStatus.ACTIVE);
        accountsDTO.setCurrency("USD");
        accountsDTO.setInterestRate(3.5);
    }

    @Test
    void account_creation_success() {
        UsersModel userModel = new UsersModel();
        userModel.setId(usersDTO.getId());
        when(usersRepository.findUsersById(usersDTO.getId())).thenReturn(userModel);

        ArgumentCaptor<AccountsModel> captor = ArgumentCaptor.forClass(AccountsModel.class);
        accountsService.createAccount(accountsDTO);
        verify(accountsRepository).insertAccounts(captor.capture());
        AccountsModel capturedModel = captor.getValue();

        assertEquals(accountsDTO.getUserId(), capturedModel.getUsersModel().getId());
        assertEquals(accountsDTO.getAccountType(), capturedModel.getAccountType());
        assertEquals(accountsDTO.getBalance(), capturedModel.getBalance());
        assertEquals(accountsDTO.getCurrency(), capturedModel.getCurrency());
        assertEquals(accountsDTO.getStatus(), capturedModel.getStatus());
        assertEquals(accountsDTO.getInterestRate(), capturedModel.getInterestRate());
    }

    @Test
    void null_values_account_creation() {
        accountsDTO.setAccountNumber(null);
        //checks if the create account service throws the IllegalArgumentException or not since we provided a value as null
        assertThrows(InvalidDataException.class, () -> accountsService.createAccount(accountsDTO));
        verify(accountsRepository, never()).insertAccounts(any());
    }

    @Test
    void successful_getAccountById() {
        UsersModel userModel = new UsersModel();
        userModel.setId(usersDTO.getId());

        AccountsModel accountModel = AccountsModel.builder()
                .usersModel(userModel)
                .accountType(accountsDTO.getAccountType())
                .balance(accountsDTO.getBalance())
                .currency(accountsDTO.getCurrency())
                .status(accountsDTO.getStatus())
                .interestRate(accountsDTO.getInterestRate())
                .build();

        when(usersRepository.findUsersById(usersDTO.getId())).thenReturn(userModel);
        when(accountsRepository.findAccountById(usersDTO.getId())).thenReturn(accountModel);

        AccountsModel result = accountsService.getAccountsById(usersDTO.getId());

        verify(accountsRepository).findAccountById(usersDTO.getId());
        verify(usersRepository).findUsersById(usersDTO.getId());

        assertNotNull(result);
        assertEquals(usersDTO.getId(), result.getUsersModel().getId());
        assertEquals(accountsDTO.getAccountType(), result.getAccountType());
        assertEquals(accountsDTO.getBalance(), result.getBalance());
        assertEquals(accountsDTO.getCurrency(), result.getCurrency());
        assertEquals(accountsDTO.getStatus(), result.getStatus());
        assertEquals(accountsDTO.getInterestRate(), result.getInterestRate());
    }

    //
    @Test
    void getAccount_nullUserId() {
        accountsDTO.setUserId(null);
        assertThrows(IllegalArgumentException.class, () -> accountsService.getAccountsById(accountsDTO.getUserId()));
        verify(accountsRepository, never()).findAccountById(any());
    }

    @Test
    void getAccount_negativeUserId() {
        accountsDTO.setUserId(-1L);
        assertThrows(IllegalArgumentException.class, () -> accountsService.getAccountsById(accountsDTO.getUserId()));
        verify(accountsRepository, never()).findAccountById(any());
    }

    //
    @Test
    void deleteAccount_successful() {
        UsersModel userModel = new UsersModel();
        userModel.setId(usersDTO.getId());

        when(usersRepository.findUsersById(usersDTO.getId())).thenReturn(userModel);
        accountsService.deleteAccountById(usersDTO.getId());
        verify(usersRepository).findUsersById(usersDTO.getId());
        verify(accountsRepository).deleteAccountById(usersDTO.getId());
        verify(usersRepository, never()).deleteUsersById(any());
    }

    @Test
    void deleteAccount_userId_null() {
        accountsDTO.setUserId(null);
        assertThrows(IllegalArgumentException.class, () -> accountsService.deleteAccountById(accountsDTO.getUserId()));
        verify(accountsRepository, never()).deleteAccountById(any());
    }

    @Test
    void getAllAccounts_success() {
        int page = 1;
        int size = 10;
        int offset = (page - 1) * size;
        accountsService.getAllAccounts(page, size);
        verify(accountsRepository).findAllAccounts(offset, size);
    }

    @Test
    void getAllAccounts_whenNoAccounts_returnsEmptyList() {
        int page = 1;
        int size = 10;
        int offset = (page - 1) * size;
        when(accountsRepository.findAllAccounts(offset, size)).thenReturn(Collections.emptyList()); //emptyList() is the part of the collections library this gives an immutable empty list i.e. can't do any operation here
        List<AccountsModel> result = accountsService.getAllAccounts(page, size);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}