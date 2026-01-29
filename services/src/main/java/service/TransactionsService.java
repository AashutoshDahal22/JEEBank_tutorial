package service;

import exception.InSufficientBalanceException;
import exception.InvalidAmountException;
import exception.InvalidUserException;
import interfaces.TransactionsRepositoryInterface;
import interfaces.TransactionsServiceInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import models.TransactionsModel;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransactionsService implements TransactionsServiceInterface {

    private final TransactionsRepositoryInterface transactionsRepository;

    @Inject
    public TransactionsService(@Named("mybatisTransactions") TransactionsRepositoryInterface transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Double checkBalance(Long userId) {
        if (userId == null) throw new InvalidUserException("No user to fetch balance form");
        return transactionsRepository.fetchBalance(userId);
    }

    @Override
    public void withdraw(TransactionsModel transactions) {
        double balance = transactionsRepository.fetchBalance(transactions.getUserId());
        if (transactions.getAmount() == null || transactions.getAmount() < 0)
            throw new InvalidAmountException("Cannot withdraw negative amount");
        if (balance < transactions.getAmount())
            throw new InSufficientBalanceException("Cannot process withdrawal insufficient amount in you account brokie");
        transactionsRepository.withdraw(transactions);
    }

    @Override
    public void deposit(TransactionsModel transactions) {
        if (transactions.getAmount() == null || transactions.getAmount() < 0)
            throw new InvalidAmountException("Cannot deposit negative amount");
        transactionsRepository.deposit(transactions);
    }

    @Transactional
    public void transfer(TransactionsModel transactions) {
        //retrieves the senders balance to check with the amount
        double balance = transactionsRepository.fetchBalance(transactions.getUserId());
        if (balance < transactions.getAmount()) throw new InSufficientBalanceException("No funds to transfer");
        if (transactions.getUserId().equals(transactions.getTargetAccountUserId()))
            throw new IllegalArgumentException("Cannot transfer to same account");
        //performs the transactions
        transactionsRepository.withdraw(transactions);
//        double balanceAfterWithdraw = transactionsRepository.fetchBalance(transactions.getUserId());
//        System.out.println("Balance after withdraw but before transfer" + balanceAfterWithdraw);
//        if (true) throw new RuntimeException("should rollback the transaction");
        transactionsRepository.depositTo(transactions);
    }
}
