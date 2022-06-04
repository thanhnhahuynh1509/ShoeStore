package com.dacs.usermodules.security;

import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginDefaultSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final CustomerService customerService;

    public LoginDefaultSuccessHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String email = ((CustomerDetails) authentication.getPrincipal()).getUsername();
        Customer customer = customerService.getByEmail(email);
        if(customer != null) {
            customer.setType("DEFAULT");
            customerService.save(customer);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
