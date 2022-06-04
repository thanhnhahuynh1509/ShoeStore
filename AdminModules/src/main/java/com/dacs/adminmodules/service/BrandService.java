package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.BrandRepository;
import com.dacs.entitymodules.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand saveOrUpdate(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> list() {
        return (List<Brand>) brandRepository.findAll();
    }

    public Brand get(Integer id) {
        return brandRepository.findById(id).orElseGet(() -> null);
    }

    public Brand getByName(String name) {
        return brandRepository.findByName(name).orElseGet(() -> null);
    }

    public void delete(Integer id) {
        brandRepository.deleteById(id);
    }
}
