package interfaces.transactions;

import models.TransactionsModel;

public interface TransactionsRepositoryInterface {

    Double fetchBalance(Long userId);

    void withdraw(TransactionsModel transactions);

    void deposit(TransactionsModel transactions);

    void depositTo(TransactionsModel transactions);
}

