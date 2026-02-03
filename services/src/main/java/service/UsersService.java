package service;

import DTO.UsersDTO;
import DTO.UsersPatchDTO;
import exception.InvalidDataException;
import exception.InvalidEmailException;
import interfaces.users.UsersRepositoryInterface;
import interfaces.users.UsersServiceInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import models.UsersModel;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

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
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

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
    @Transactional

    public UsersModel getUsersById(Long id) {
        if (id == null || id < 0) throw new InvalidDataException("cant be null or negative");
        return usersRepository.findUsersById(id);
    }

    @Override
    @Transactional

    public UsersModel getUsersByEmail(String email) {
        if (email == null) throw new InvalidDataException("cant be null or negative");
        return usersRepository.findUsersByEmail(email);
    }

    @Override
    @Transactional
    public void updateUsers(Long id, UsersPatchDTO dto) {

        UsersModel existingUser= this.usersRepository.findUsersById(id);

        if (dto.getBirthdate() != null && dto.getBirthdate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Future data cannot be entered in birthdate");
        }

        if (dto.getName() != null) existingUser.setName(dto.getName());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getAddress() != null) existingUser.setAddress(dto.getAddress());
        if (dto.getBirthdate() != null) existingUser.setBirthdate(dto.getBirthdate());
        if (dto.getPhoneNumber() != null) existingUser.setPhoneNumber(dto.getPhoneNumber());
    }

    @Override
    @Transactional

    public void deleteUsersById(Long id) {
        if (id == null || id < 0) throw new InvalidDataException("cant be null or negative");
        usersRepository.deleteUsersById(id);
    }

    @Override
    @Transactional

    public List<UsersModel> getAllUsers(int page, int size) {
        if (page == 0 && size == 0)
            throw new InvalidDataException("Size and page cannot be null since they are query params");
        int offset = (page - 1) * size;
        return usersRepository.getAllUsers(offset, size);
    }

}
