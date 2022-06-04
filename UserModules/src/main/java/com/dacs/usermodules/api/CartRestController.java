package com.dacs.usermodules.api;

import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Product;
import com.dacs.usermodules.service.CartService;
import com.dacs.usermodules.service.CustomerService;
import com.dacs.usermodules.utils.CustomerLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {

    private final CartService cartService;
    private final CustomerService customerService;

    public CartRestController(CartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public String addToCart(HttpServletRequest request, Integer productId, Integer quantity) {
        String email = CustomerLoginUtils.getEmail(request);
        Product product = new Product(productId);
        Customer customer = customerService.getByEmail(email);
        return cartService.addCart(product, customer, quantity);
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        cartService.delete(id);
        return "OK";
    }

    @GetMapping("/update")
    public String update(Integer id, Integer quantity) {
        cartService.updateCart(id, quantity);
        return "OK";
    }
}
