package com.iliev.domani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DomaniApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomaniApplication.class, args);
    }

}
