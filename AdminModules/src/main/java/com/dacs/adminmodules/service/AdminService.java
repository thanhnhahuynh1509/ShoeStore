package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.AdminRepository;
import com.dacs.entitymodules.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    private void encodePassword(Admin admin) {
        String password = admin.getPassword();
        admin.setPassword(passwordEncoder.encode(password));
    }

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin saveOrUpdate(Admin admin) {
        if(admin.getId() != null) {
            Admin adminDb = get(admin.getId());

            if(admin.getPassword().trim().isEmpty()) {
                admin.setPassword(adminDb.getPassword());
            } else {
                encodePassword(admin);
            }
        } else {
            encodePassword(admin);
        }
        return adminRepository.save(admin);
    }

    public List<Admin> list() {
        return (List<Admin>) adminRepository.findAll();
    }

    public Admin get(Integer id) {
        return adminRepository.findById(id).orElseGet(() -> null);
    }

    public Admin getByEmail(String email) {
        return adminRepository.findByEmail(email).orElseGet(() -> null);
    }

    public void changeStatus(Integer id, boolean status) {
        adminRepository.changeStatus(id, status);
    }

    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }
}
