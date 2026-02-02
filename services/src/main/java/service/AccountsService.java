package service;

import DTO.AccountStatusUpdateDTO;
import DTO.AccountsDTO;
import accounts.AccountsStatus;
import exception.InvalidDataException;
import interfaces.accounts.AccountsRepositoryInterface;
import interfaces.accounts.AccountsServiceInterface;
import interfaces.users.UsersRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import models.AccountsModel;
import jakarta.inject.Inject;
import models.UsersModel;

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
    @Transactional
    public void createAccount(AccountsDTO dto) {
        if (dto.getUserId() == null) {
            throw new InvalidDataException("UserId is required");
        }
        UsersModel user = usersRepository.findUsersById(dto.getUserId());
        if (user == null) {
            throw new InvalidDataException("UserId is required and must exist");
        }
        AccountsModel accountsModel = AccountsModel.builder()
                .accountType(dto.getAccountType())
                .balance(dto.getBalance())
                .currency(dto.getCurrency())
                .interestRate(dto.getInterestRate())
                .status(dto.getStatus())
                .usersModel(user)
                .build();
//        AccountsModel accountsModel = ReflectionMapper.map(dto, AccountsModel.class);
        accountsRepository.insertAccounts(accountsModel);
    }

    @Override
    @Transactional
    public void updateAccountStatus(Long accountNumber, AccountStatusUpdateDTO dto) {
        accountsRepository.updateAccountStatus(accountNumber, dto.getStatus());
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
