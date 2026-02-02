package hibernate.repository;

import accounts.AccountsStatus;
import interfaces.accounts.AccountsRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import models.AccountsModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped //since they don't hold client state between method calls
@Named("hibernateAccounts")
public class AccountsRepository implements AccountsRepositoryInterface {

    @PersistenceContext(unitName = "JeebankPU")
    private EntityManager entityManager;

    @Override
    public void insertAccounts(AccountsModel accountsModel) {
        entityManager.persist(accountsModel);
    }

    @Override
    public AccountsModel findAccountById(Long userId) {
        return entityManager.createQuery("SELECT a from AccountsModel a where a.usersModel.id=:userId", AccountsModel.class).setParameter("userId", userId).getSingleResult();
    }

    @Override
    public void updateAccountStatus(Long accountNumber, AccountsStatus accountsStatus){
        entityManager.createQuery("UPDATE AccountsModel a SET a.status= :status where a.accountNumber=:accountNumber").setParameter("status", accountsStatus).setParameter("accountNumber",accountNumber).executeUpdate();
    }


    @Override
    public void deleteAccountById(Long userId) {
        AccountsModel account = entityManager.createQuery("SElECT a from AccountsModel a where a.usersModel.id=:userId", AccountsModel.class).setParameter("userId", userId).getSingleResult();

        if (account != null) {
            entityManager.remove(account);
        }
    }

    @Override
    public List<AccountsModel> findAllAccounts(int offset, int size) {
        return entityManager.createQuery("SELECT a from AccountsModel a", AccountsModel.class).setFirstResult(offset).setMaxResults(size).getResultList();
    }
}
