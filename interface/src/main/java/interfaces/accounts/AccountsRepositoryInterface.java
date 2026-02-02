package interfaces.accounts;

import models.AccountsModel;
import accounts.AccountsStatus;

import java.util.List;

public interface AccountsRepositoryInterface {

    void insertAccounts(AccountsModel accountsModel);

    AccountsModel findAccountById(Long userId);

    void deleteAccountById(Long userId);

    List<AccountsModel> findAllAccounts(int offset, int size);

    void updateAccountStatus(Long accountNumber, AccountsStatus accountsStatus);
}
