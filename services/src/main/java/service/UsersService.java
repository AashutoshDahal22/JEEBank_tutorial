package service;

import DTO.UsersDTO;
import exception.InvalidDataException;
import exception.InvalidEmailException;
import interfaces.UsersRepositoryInterface;
import interfaces.UsersServiceInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import models.UsersModel;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UsersService implements UsersServiceInterface {

    @Inject
    @Named("hibernateUsers")
    private UsersRepositoryInterface usersRepository;

    public UsersService() {
    }

    @Override
    @Transactional
    public void createUser(UsersDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new InvalidEmailException("Email is Required");
        }

        if (dto.getBirthdate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Future data cannot be entered in birthdate");
        }
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(dto.getPassword(), org.mindrot.jbcrypt.BCrypt.gensalt());

        UsersModel usersModel = UsersModel.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(hashedPassword)
                .address(dto.getAddress())
                .birthdate(dto.getBirthdate())
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .build();
        usersRepository.insertUsers(usersModel);
    }

    @Override
    public UsersModel getUsersById(Long id) {
        if (id == null || id < 0) throw new InvalidDataException("cant be null or negative");
        return usersRepository.findUsersById(id);
    }

    @Override
    public UsersModel getUsersByEmail(String email) {
        if (email == null) throw new InvalidDataException("cant be null or negative");
        return usersRepository.findUsersByEmail(email);
    }

    @Override
    @Transactional
    public void updateUsers(UsersDTO dto) {
        if (dto.getId() == null || dto.getId() == -1) {
            throw new InvalidDataException("Users with null or negative users id cannot be updated");
        }
        if (dto.getBirthdate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Future data cannot be entered in birthdate");
        }
        UsersModel usersModel = UsersModel.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .birthdate(dto.getBirthdate())
                .phoneNumber(dto.getPhoneNumber())
                .build();
//        UsersModel usersModel = ReflectionMapper.map(dto, UsersModel.class);
        usersRepository.updateUsers(usersModel);
    }

    @Override
    public void deleteUsersById(Long id) {
        if (id == null || id < 0) throw new InvalidDataException("cant be null or negative");
        usersRepository.deleteUsersById(id);
    }

    @Override
    public List<UsersModel> getAllUsers(int page, int size) {
        if (page == 0 && size == 0)
            throw new InvalidDataException("Size and page cannot be null since they are query params");
        int offset = (page - 1) * size;
        return usersRepository.getAllUsers(offset, size);
    }

}
