package interfaces.accounts;

import DTO.AccountStatusUpdateDTO;
import DTO.AccountsDTO;
import accounts.AccountsStatus;
import models.AccountsModel;

import java.util.List;

public interface AccountsServiceInterface {
    void createAccount(AccountsDTO dto);

    AccountsModel getAccountsById(Long userId);

    void deleteAccountById(Long userId);

    List<AccountsModel> getAllAccounts(int page, int size);

    void updateAccountStatus(Long accountNumber, AccountStatusUpdateDTO dto);
}
