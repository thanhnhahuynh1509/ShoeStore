package com.dacs.usermodules.service;

import com.dacs.entitymodules.Category;
import com.dacs.usermodules.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> listAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public List<Category> listCategoriesHaveProducts() {
        return (List<Category>) categoryRepository.findAllCategoriesHaveProducts();
    }

    public List<Category> getCategoriesBreadcrumb(Category category) {
        List<Category> categoriesBreadcrumb = new ArrayList<>();
        Category parent = category.getParent();
        while(parent != null) {
            categoriesBreadcrumb.add(0, parent);
            parent = parent.getParent();
        }
        categoriesBreadcrumb.add(category);
        return categoriesBreadcrumb;
    }
}
