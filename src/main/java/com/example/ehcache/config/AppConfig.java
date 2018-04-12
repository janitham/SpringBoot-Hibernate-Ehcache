package com.example.ehcache.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.persistence.EntityManagerFactory;

@Configuration
public class AppConfig {

    //@Bean
    public SessionFactory getSessionFactory(EntityManagerFactory entityManagerFactory) {
        Assert.notNull(entityManagerFactory.unwrap(SessionFactory.class), "Session factory is not hibernate");
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
}
