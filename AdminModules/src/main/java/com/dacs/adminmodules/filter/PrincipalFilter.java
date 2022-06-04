package com.dacs.adminmodules.filter;

import com.dacs.adminmodules.service.AdminService;
import com.dacs.entitymodules.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class PrincipalFilter implements Filter {

    private final AdminService adminService;

    public PrincipalFilter(AdminService adminService) {
        this.adminService = adminService;
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
        if(request.getUserPrincipal() != null) {
            String email = request.getUserPrincipal().getName();
            Admin admin = adminService.getByEmail(email);
            request.setAttribute("admin", admin);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
