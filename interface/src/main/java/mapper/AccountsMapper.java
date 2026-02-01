package mapper;

import models.AccountsModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountsMapper {

    void insertAccounts(AccountsModel accounts);

    AccountsModel findAccountById(Long userid);

    void deleteAccountById(Long userid);

    List<AccountsModel> findAllAccounts(@Param("offset") int offset, @Param("size") int size);
}


//    @Insert("INSERT INTO accountdetails(accountnumber,balance,userid,status,account_type,currency,interest_rate) values (#{accountNumber},#{balance},#{user.id},#{status},#{accountType},#{currency},#{interestRate})")
//    @Select("SELECT accountnumber,balance,userid,status,account_type,currency,interest_rate from accountdetails")
//    @Delete("DELETE FROM accountdetails where userid=#{user.id}")
//    @Update("UPDATE accountdetails SET accountnumber=#{accountNumber},balance=#{balance},userid=#{userid},status=#{status},account_type=#{accountType},currency=#{currency},interest_rate=#{interestRate} where userid=#{userid}")
//    @Select("SELECT * from accountdetails where userid=#{user.id}")
