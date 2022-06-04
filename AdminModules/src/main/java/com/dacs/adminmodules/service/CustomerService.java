package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.CustomerRepository;
import com.dacs.entitymodules.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> list() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer get(Integer id) {
        return customerRepository.findById(id).orElseGet(() -> null);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }
}
