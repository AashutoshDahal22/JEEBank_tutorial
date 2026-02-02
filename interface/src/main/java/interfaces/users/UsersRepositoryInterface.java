package interfaces.users;

import models.UsersModel;

import java.util.List;

public interface UsersRepositoryInterface {

    void insertUsers(UsersModel usersModel);

    UsersModel findUsersByEmail(String email);

    UsersModel findUsersById(Long id);

    void updateUsers(UsersModel usersModel);

    void deleteUsersById(Long id);

    List<UsersModel> getAllUsers(int offset, int size);

}
