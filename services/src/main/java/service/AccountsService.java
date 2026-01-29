package service;

import DTO.AccountsDTO;
import exception.InvalidEmailException;
import interfaces.AccountsRepositoryInterface;
import interfaces.AccountsServiceInterface;
import interfaces.UsersRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import models.AccountsModel;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AccountsService implements AccountsServiceInterface {

    private final AccountsRepositoryInterface accountsRepository;
    private final UsersRepositoryInterface usersRepository;

    @Inject
    public AccountsService(@Named("hibernateAccounts") AccountsRepositoryInterface accountsRepository, @Named("hibernateUsers") UsersRepositoryInterface usersRepository) {
        this.accountsRepository = accountsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void createAccount(AccountsDTO dto) {
        if (usersRepository.findUsersById(dto.getUserId()) == null) {
            throw new InvalidEmailException("UserId is required");
        }
//        AccountsModel accountsModel = AccountsModel.builder()
//                .accountType(dto.getAccountType())
//                .balance(dto.getBalance())
//                .currency(dto.getCurrency())
//                .interestRate(dto.getInterestRate())
//                .status(dto.getStatus())
//                .usersModel(usersRepository.findUsersById(dto.getUserId()))
//                .build();
        AccountsModel accountsModel = ReflectionMapper.map(dto, AccountsModel.class);
        accountsRepository.insertAccounts(accountsModel);
    }

    @Override
    public AccountsModel getAccountsById(Long userId) {
        if (usersRepository.findUsersById(userId) == null)
            throw new IllegalArgumentException("Userid cannot be null");
        return accountsRepository.findAccountById(userId);
    }

    @Override
    public void deleteAccountById(Long userId) {
        if (usersRepository.findUsersById(userId) == null)
            throw new IllegalArgumentException("Userid cannot be null");
        accountsRepository.deleteAccountById(userId);
    }

    @Override
    public List<AccountsModel> getAllAccounts(int page, int size) {
        int offset = (page - 1) * size;
        return accountsRepository.findAllAccounts(offset, size);
    }

}
