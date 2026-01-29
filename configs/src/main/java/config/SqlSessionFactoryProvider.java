package config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

import java.io.InputStream;

@ApplicationScoped
public class SqlSessionFactoryProvider {

    @Produces
    @ApplicationScoped
    @SessionFactoryProvider
    public SqlSessionFactory produceFactory() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mybatis-config.xml")) { //get class loader looks inside the appliction path of our project for the xml file //getclass references the current class
            if (inputStream == null) {
                throw new IllegalStateException("mybatis-config.xml not found in classpath");
            }
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SqlSessionFactory", e);
        }
    }
}