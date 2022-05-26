package ru.itmo.kotiki.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.itmo.kotiki.dao", "ru.itmo.kotiki.service"})
@EntityScan(basePackages = "ru.itmo.kotiki.dao.entity")
@EnableJpaRepositories(basePackages = "ru.itmo.kotiki.dao.repository")
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
