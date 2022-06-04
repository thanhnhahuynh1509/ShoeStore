package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.ProductRepository;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.entitymodules.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveOrUpdate(Product product) {
        if(product.getId() == null)
            product.setCreatedTime(new Date());
        String alias = AliasNameMaker.make(product.getName());
        product.setAlias(alias);
        product.setUpdatedTime(new Date());
        return productRepository.save(product);
    }

    public List<Product> list() {
        return (List<Product>) productRepository.findAll();
    }

    public List<Product> listByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product get(Integer id) {
        return productRepository.findById(id).orElseGet(() -> null);
    }

    public Product getByName(String name) {
        return productRepository.findByName(name).orElseGet(() -> null);
    }

    public void changeStatus(Integer id, boolean status) {
        productRepository.changeStatus(id, status);
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
