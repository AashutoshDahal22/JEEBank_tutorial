package interfaces;

import DTO.AccountsDTO;
import models.AccountsModel;

import java.util.List;

public interface AccountsServiceInterface {
    void createAccount(AccountsDTO dto);

    AccountsModel getAccountsById(Long userId);

    void deleteAccountById(Long userId);

    List<AccountsModel> getAllAccounts(int page, int size);
}
