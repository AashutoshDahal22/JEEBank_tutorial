package interfaces;

import models.UsersModel;

import java.util.List;

public interface UsersRepositoryInterface {

    void insertUsers(UsersModel usersModel);

    UsersModel findUsersById(Long id);

    void updateUsers(UsersModel usersModel);

    void deleteUsersById(Long id);

    List<UsersModel> getAllUsers(int offset, int size);

//    List<UsersModel> searchUserByName(String keyword);
//
//    void indexExistingUser();
}
