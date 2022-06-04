package com.dacs.usermodules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.dacs.entitymodules"})
public class UserModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserModulesApplication.class, args);
    }

}
