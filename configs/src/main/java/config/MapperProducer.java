package config;

import mapper.AccountsMapper;
import mapper.TransactionsMapper;
import mapper.UsersMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.apache.ibatis.session.SqlSession;

@RequestScoped
public class MapperProducer {

    @Inject
    private SqlSession sqlSession; //from mybatis cdi which is made by mybatis from the SqlSessionFactory we provided

    @Produces
    @RequestScoped
    public TransactionsMapper produceTransactionsMapper() {
        return sqlSession.getMapper(TransactionsMapper.class);
    }

    @Produces
    @RequestScoped
    public UsersMapper produceUsersMapper() {
        return sqlSession.getMapper(UsersMapper.class);
    }

    @Produces
    @RequestScoped
    public AccountsMapper produceAccountsMapper() {
        return sqlSession.getMapper(AccountsMapper.class);
    }
}
