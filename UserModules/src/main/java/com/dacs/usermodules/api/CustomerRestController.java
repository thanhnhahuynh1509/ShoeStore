package com.dacs.usermodules.api;

import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.security.WebSecurityConfig;
import com.dacs.usermodules.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/check-email-unique")
    public String checkEmailUnique(String email) {
        return customerService.getByEmail(email) == null ? "OK" : "NOT OK";
    }

    @PostMapping("/check-password-correct")
    public String checkPasswordCorrect(String email, String password) {

        Customer customer = customerService.getByEmail(email);
        if(customer != null && WebSecurityConfig.passwordEncoder().matches(password, customer.getPassword())) {
            return "OK";
        }
        return "NOT OK";
    }
}
