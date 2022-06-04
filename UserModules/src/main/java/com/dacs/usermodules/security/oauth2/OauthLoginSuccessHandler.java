package com.dacs.usermodules.security.oauth2;

import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.service.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OauthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final CustomerService customerService;

    public OauthLoginSuccessHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomerOauth2User customerOauth2User = (CustomerOauth2User) authentication.getPrincipal();
        String name = customerOauth2User.getName();
        String email = customerOauth2User.getEmail();

        Customer customer = customerService.getByEmail(email);

        if(customer == null) {
            customer = new Customer();
            customer.setEmail(email);
            customer.setFullName(name);
        }
        customer.setType("OAUTH");
        customerService.save(customer);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
