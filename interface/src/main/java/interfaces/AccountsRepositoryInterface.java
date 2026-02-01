package interfaces;

import models.AccountsModel;

import java.util.List;

public interface AccountsRepositoryInterface {

    void insertAccounts(AccountsModel accountsModel);

    AccountsModel findAccountById(Long userId);

    void deleteAccountById(Long userId);

    List<AccountsModel> findAllAccounts(int offset, int size);
}
