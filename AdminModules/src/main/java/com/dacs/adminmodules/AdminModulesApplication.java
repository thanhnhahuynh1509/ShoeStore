package com.dacs.adminmodules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.dacs.entitymodules"})
public class AdminModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminModulesApplication.class, args);
    }

}
