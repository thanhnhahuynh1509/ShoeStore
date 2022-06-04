package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.CategoryPostRepository;
import com.dacs.adminmodules.repository.CategoryRepository;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.entitymodules.CategoryPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryPostService {
    private final CategoryPostRepository categoryPostRepository;

    public CategoryPostService(CategoryPostRepository categoryPostRepository) {
        this.categoryPostRepository = categoryPostRepository;
    }

    public CategoryPost saveOrUpdate(CategoryPost categoryPost) {
        categoryPost.setAlias(AliasNameMaker.make(categoryPost.getName()));
        return categoryPostRepository.save(categoryPost);
    }

    public List<CategoryPost> listAll() {
        return (List<CategoryPost>) categoryPostRepository.findAll();
    }

    public CategoryPost get(Integer id) {
        return categoryPostRepository.findById(id).orElseGet(() -> null);
    }

    public CategoryPost getByName(String name) {
        return categoryPostRepository.findByName(name);
    }

    public void delete(Integer id) {
        categoryPostRepository.deleteById(id);
    }
}
