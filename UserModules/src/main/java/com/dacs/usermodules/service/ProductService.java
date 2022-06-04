package com.dacs.usermodules.service;

import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Product;
import com.dacs.usermodules.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listNewProducts() {
        return productRepository.findAllByOrderIdDesc();
    }

    public List<Product> listProductsByCategoryAndExceptProduct(Category category, Integer productId) {
        return productRepository.findAllByCategoryAndExceptProduct(category, productId);
    }

    public List<Product> listProductsByCategoryAlias(String alias) {
        return productRepository.findAllByCategoryAlias(alias);
    }

    public Product getByAlias(String alias) {
        return productRepository.findByAlias(alias);
    }
}
