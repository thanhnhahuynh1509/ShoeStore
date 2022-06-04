package com.dacs.usermodules.service;

import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.repository.CustomerRepository;
import com.dacs.usermodules.security.WebSecurityConfig;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.StringTokenizer;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer register(Customer customer) {
        String token = RandomString.make(64);
        customer.setFullName(customer.getEmail());
        customer.setVerifyToken(token);
        encodePassword(customer);
        return customerRepository.save(customer);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer resetPassword(Customer customer) {
        encodePassword(customer);
        return customerRepository.save(customer);
    }

    public void enabled(Integer customerId) {
        customerRepository.enabled(customerId);
    }

    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer getByVerifyToken(String token) {
        return customerRepository.findByVerifyToken(token);
    }

    public Customer getByForgetToken(String token) {
        return customerRepository.findByForgetToken(token);
    }

    private void encodePassword(Customer customer) {
        String password = customer.getPassword();
        password = WebSecurityConfig.passwordEncoder().encode(password);
        customer.setPassword(password);
    }
}
