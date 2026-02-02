package hibernate.repository;

import interfaces.transactions.TransactionsRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import models.TransactionsModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
@Named("hibernateTransactions")
public class TransactionsRepository implements TransactionsRepositoryInterface {

    @PersistenceContext(name = "JeebankPU")
    private EntityManager entityManager;


    @Override
    public Double fetchBalance(Long userId) {
        return entityManager.createQuery("select a.balance from AccountsModel a where a.usersModel.id=:userId", Double.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public void withdraw(TransactionsModel transactionsModel) {
        Double amount = transactionsModel.getAmount();
        Long userId = transactionsModel.getUserId();

        entityManager.createQuery("update AccountsModel  a set a.balance=a.balance- :amount where a.usersModel.id=:userId")
                .setParameter("amount", amount)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public void deposit(TransactionsModel transactionsModel) {
        Double amount = transactionsModel.getAmount();
        Long userId = transactionsModel.getUserId();

        entityManager.createQuery("update AccountsModel  a set a.balance=a.balance+ :amount where a.usersModel.id=:userId")
                .setParameter("amount", amount)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public void depositTo(TransactionsModel transactionsModel) {
        Double amount = transactionsModel.getAmount();
        Long targetAccountUserId = transactionsModel.getTargetAccountUserId();

        entityManager.createQuery("update AccountsModel  a set a.balance=a.balance+ :amount where a.usersModel.id=:userId")
                .setParameter("amount", amount)
                .setParameter("userId", targetAccountUserId)
                .executeUpdate();
    }
}
