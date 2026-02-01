package mybatis.repository;

import interfaces.TransactionsRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import mapper.TransactionsMapper;
import models.TransactionsModel;
import jakarta.inject.Inject;

@ApplicationScoped
@Named("mybatisTransactions")

public class TransactionsRepository implements TransactionsRepositoryInterface {

    @Inject
    private TransactionsMapper transactionsMapper;

    @Override
    public Double fetchBalance(Long userId) {
        return transactionsMapper.fetchBalance(userId);
    }

    @Override
    public void withdraw(TransactionsModel transactionsModel) {
        transactionsMapper.withdraw(transactionsModel);
    }

    @Override
    public void deposit(TransactionsModel transactionsModel) {
        transactionsMapper.deposit(transactionsModel);
    }

    @Override
    public void depositTo(TransactionsModel transactionsModel) {
        transactionsMapper.depositTo(transactionsModel);
    }
}
