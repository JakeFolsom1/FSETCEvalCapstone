package com.fsetcevals.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Created following a guide posted by baeldung
// https://www.baeldung.com/spring-security-cas-sso

@SpringBootApplication
public class ClientApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ClientApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
