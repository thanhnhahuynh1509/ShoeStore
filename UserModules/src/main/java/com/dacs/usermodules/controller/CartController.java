package com.dacs.usermodules.controller;

import com.dacs.entitymodules.Cart;
import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.service.CartService;
import com.dacs.usermodules.service.CustomerService;
import com.dacs.usermodules.utils.CustomerLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;

    public CartController(CartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        String email = CustomerLoginUtils.getEmail(request);
        Customer customer = customerService.getByEmail(email);
        List<Cart> carts = cartService.listByCustomer(customer);
        model.addAttribute("totalQuantity", getTotalQuantity(carts));
        model.addAttribute("totalPrice", getTotalPrice(carts));
        model.addAttribute("carts", carts);

        return "/cart/index";
    }

    private Integer getTotalQuantity(List<Cart> carts) {
        Integer total = 0;
        for(Cart cart : carts) {
            total += cart.getQuantity();
        }
        return total;
    }

    private double getTotalPrice(List<Cart> carts) {
        double total = 0;
        for(Cart cart : carts) {
            total += cart.getTotalPrice();
        }
        return total;
    }
}
