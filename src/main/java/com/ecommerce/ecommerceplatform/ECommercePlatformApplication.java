package com.ecommerce.ecommerceplatform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ECommercePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommercePlatformApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return runner -> {
            System.out.println("Hello World");
        };
    }
}
