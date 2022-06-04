package com.dacs.usermodules.filter;

import com.dacs.usermodules.service.CustomerService;
import com.dacs.usermodules.utils.CustomerLoginUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class PrincipalFilter implements Filter {

    private final CustomerService customerService;

    public PrincipalFilter(CustomerService customerService) {
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
        CustomerLoginUtils.setCustomerForFilter(request, customerService);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
