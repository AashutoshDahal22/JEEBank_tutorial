package mybatis.repository;

import interfaces.UsersRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import mapper.UsersMapper;
import models.UsersModel;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
@Named("mybatisUsers")

public class UsersRepository implements UsersRepositoryInterface {

    @Inject
    private UsersMapper usersMapper;

    @Override
    public void insertUsers(UsersModel usersModel) {
        usersMapper.insertUsers(usersModel);
    }

    @Override
    public UsersModel findUsersById(Long id) {
        return usersMapper.findUsersById(id);
    }

    @Override
    public UsersModel findUsersByEmail(String email) {
        return usersMapper.findUsersByEmail(email);
    }

    @Override
    public void updateUsers(UsersModel usersModel) {
        usersMapper.updateUsers(usersModel);
    }

    @Override
    public void deleteUsersById(Long id) {
        usersMapper.deleteUsersById(id);
    }

    @Override
    public List<UsersModel> getAllUsers(int offset, int size) {
        return usersMapper.getAllUsers(offset, size);
    }

}
