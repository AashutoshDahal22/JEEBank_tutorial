package mybatis.repository;

import accounts.AccountsStatus;
import interfaces.accounts.AccountsRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import mapper.AccountsMapper;
import models.AccountsModel;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
@Named("mybatisAccounts")
public class AccountsRepository implements AccountsRepositoryInterface {

    @Inject
    private AccountsMapper accountsMapper;

    @Override
    public void insertAccounts(AccountsModel accountsModel) {
        accountsMapper.insertAccounts(accountsModel);
    }

    @Override
    public void updateAccountStatus(Long accountNumber, AccountsStatus accountsStatus) {
        accountsMapper.updateAccountStatus(accountNumber, accountsStatus);
    }

    @Override
    public AccountsModel findAccountById(Long userId) {
        return accountsMapper.findAccountById(userId);
    }

    @Override
    public void deleteAccountById(Long userId) {
        accountsMapper.deleteAccountById(userId);
    }

    @Override
    public List<AccountsModel> findAllAccounts(int offset, int size) {
        return accountsMapper.findAllAccounts(offset, size);
    }
}
