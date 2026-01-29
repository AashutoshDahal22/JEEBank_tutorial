package config;//package com.example.jeebank.config;
//
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.enterprise.inject.Produces;
//import jakarta.inject.Inject;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//
//@RequestScoped
//public class SqlSessionProducer {
//
//    @Inject
//    private SqlSessionFactory sqlSessionFactory;
//
//    @Produces
//    @RequestScoped
//    public SqlSession produceSession() {
//        return sqlSessionFactory.openSession();
//    }
//}
