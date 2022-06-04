package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.service.CustomerService;
import com.dacs.entitymodules.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Customer> customers = customerService.list();
        model.addAttribute("customers", customers);

        return "/customer/index";
    }

    @GetMapping("/details")
    public String details(Integer id, Model model) {
        Customer customer = customerService.get(id);
        model.addAttribute("customer", customer);
        return "/customer/details";
    }
}
