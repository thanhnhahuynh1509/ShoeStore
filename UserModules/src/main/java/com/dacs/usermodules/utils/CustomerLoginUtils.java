package com.dacs.usermodules.utils;

import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.security.oauth2.CustomerOauth2User;
import com.dacs.usermodules.service.CustomerService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public class CustomerLoginUtils {


    public static void setCustomerForFilter(HttpServletRequest request, CustomerService customerService) {
        String email = getEmail(request);
        Customer customer = customerService.getByEmail(email);;
        request.setAttribute("customer", customer);
    }

    public static String getEmail(HttpServletRequest request) {
        String email = null;
        Object principal = request.getUserPrincipal();
        if(principal instanceof RememberMeAuthenticationToken || principal instanceof UsernamePasswordAuthenticationToken) {
            email = ((AbstractAuthenticationToken) principal).getName();
        } else if(principal instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;
            email = ((CustomerOauth2User) authenticationToken.getPrincipal()).getEmail();
        }
        return email;
    }

}
