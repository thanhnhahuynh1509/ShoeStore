package com.dacs.adminmodules.security;

import com.dacs.adminmodules.service.AdminService;
import com.dacs.entitymodules.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getByEmail(username);

        if(admin == null)
            throw new UsernameNotFoundException("Couldn't found admin with email: " + username);

        return new AdminUserDetails(admin);
    }
}
