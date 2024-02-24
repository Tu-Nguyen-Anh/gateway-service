package com.example.springproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class SpringProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }

}
