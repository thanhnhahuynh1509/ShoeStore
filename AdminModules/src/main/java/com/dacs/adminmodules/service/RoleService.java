package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.RoleRepository;
import com.dacs.entitymodules.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> list() {
        return (List<Role>) roleRepository.findAll();
    }
}
