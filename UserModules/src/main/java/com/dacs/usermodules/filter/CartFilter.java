package com.dacs.usermodules.filter;

import com.dacs.entitymodules.Cart;
import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.service.CartService;
import com.dacs.usermodules.service.CustomerService;
import com.dacs.usermodules.utils.CustomerLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
public class CartFilter implements Filter {

    private final CartService cartService;
    private final CustomerService customerService;

    public CartFilter(CartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String url = request.getRequestURL().toString();
        if(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png")
                || url.endsWith(".jpg") || url.endsWith(".ico")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String email = CustomerLoginUtils.getEmail(request);
        Customer customer = customerService.getByEmail(email);

        if(customer != null) {
            List<Cart> carts = cartService.listByCustomer(customer);
            int size = carts.size();
            request.setAttribute("cartSize", size);
        } else {
            request.setAttribute("cartSize", 0);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
