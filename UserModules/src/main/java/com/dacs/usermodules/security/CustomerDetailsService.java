package com.dacs.usermodules.security;

import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.getByEmail(email);
        if(customer == null)
            throw new UsernameNotFoundException("Not found customer with email: " + email);
        return new CustomerDetails(customer);
    }
}
