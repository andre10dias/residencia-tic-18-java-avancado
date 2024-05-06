package com.ecomerce.projeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecomerce.projeto.mapper.UserMapper;

@Configuration
public class MapperConfig {
    
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}

