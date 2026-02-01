package interfaces;

import models.TransactionsModel;

public interface TransactionsServiceInterface {

    Double checkBalance(Long userId);

    void withdraw(TransactionsModel transactions);

    void deposit(TransactionsModel transactionsModel);

    void transfer(TransactionsModel transactions);

}
