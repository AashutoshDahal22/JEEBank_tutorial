package mapper;

import models.TransactionsModel;

public interface TransactionsMapper {

    Double fetchBalance(Long userId);

    void withdraw(TransactionsModel transactions);

    void deposit(TransactionsModel transactions);

    void depositTo(TransactionsModel transactions);

}
    //    @Select("SELECT balance from accountdetails xwhere userid = #{userId}")
    //    @Update("UPDATE accountdetails set balance=balance - #{amount} where userid=#{userId}")
    //    @Update("UPDATE accountdetails set balance=balance + #{amount} where userid=#{userId}")
    //    @Update("UPDATE accountdetails set balance=balance+#{amount} where userid=#{targetAccountUserId}")
