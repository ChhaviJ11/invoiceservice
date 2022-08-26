package com.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class Config
{
    public static Logger logger = LoggerFactory.getLogger(Config.class);
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    @PostConstruct
    public void Column()
    {
        logger.info("url: {}"+url);
        logger.info("username: {}"+username);
        logger.info("password: {}"+password);
        System.out.println("url: {}"+url);
        System.out.println("username: {}"+username);
        System.out.println("password: {}"+password);
    }

}