package com.dmdev.spring;

import com.dmdev.spring.database.pool.ConnectionPool;
import com.dmdev.spring.database.repository.CompanyRepository;
import com.dmdev.spring.database.repository.CrudRepository;
import com.dmdev.spring.ioc.Container;
import com.dmdev.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        try ( var context = new ClassPathXmlApplicationContext("application.xml");) {
            var connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);
            var companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
        }
    }
}
