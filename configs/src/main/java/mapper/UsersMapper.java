package mapper;

import models.UsersModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {
    void insertUsers(UsersModel users);

    UsersModel findUsersById(Long id);

    void updateUsers(UsersModel users);

    void deleteUsersById(Long id);

    UsersModel findUsersByEmail(String email);

    List<UsersModel> getAllUsers(@Param("offset") int offset, @Param("size") int size);
}
//    @Insert("INSERT INTO userdetails(name,email,phonenumber,address,birthdate) values (#{name}, #{email}, #{phoneNumber},#{address}, #{birthdate}::date)")
//    @Select("SELECT  id,name,email,phonenumber,address,birthdate FROM userdetails")
//    @Delete("DELETE from userdetails where id=#{id}")
//    @Update("UPDATE userdetails SET name=#{name},email=#{email},phonenumber=#{phoneNumber},address=#{address},birthdate=#{birthdate}::date WHERE id=#{id}")
//    @Select("SELECT * from userdetails where id=#{id}")
