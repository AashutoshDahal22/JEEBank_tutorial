package interfaces.users;

import DTO.UsersDTO;
import DTO.UsersPatchDTO;
import models.UsersModel;

import java.util.List;

public interface UsersServiceInterface {

    void createUser(UsersDTO dto);

    UsersModel getUsersById(Long id);

    void updateUsers(Long id, UsersPatchDTO dto);

    void deleteUsersById(Long id);

    UsersModel getUsersByEmail(String email);

    List<UsersModel> getAllUsers(int page, int size);

}
