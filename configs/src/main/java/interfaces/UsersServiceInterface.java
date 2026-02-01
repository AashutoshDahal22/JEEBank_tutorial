package interfaces;

import DTO.UsersDTO;
import models.UsersModel;

import java.util.List;

public interface UsersServiceInterface {

    void createUser(UsersDTO dto);

    UsersModel getUsersById(Long id);

    void updateUsers(UsersDTO dto);

    void deleteUsersById(Long id);

    UsersModel getUsersByEmail(String email);

    List<UsersModel> getAllUsers(int page, int size);

//    List<UsersModel> searchUserByName(String keyword);
//
//    void indexExistingUser();
}
