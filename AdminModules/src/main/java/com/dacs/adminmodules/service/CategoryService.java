package com.dacs.adminmodules.service;

import com.dacs.adminmodules.dto.CategoryDTO;
import com.dacs.adminmodules.repository.CategoryRepository;
import com.dacs.entitymodules.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private void addMarkHierarchy(Category category, int numberDash, List<CategoryDTO> categoryHierarchy) {
        Set<Category> categories = category.getChildren();
        StringBuilder mark = new StringBuilder();
        mark.append("..".repeat(numberDash));

        for (Category item : categories) {
            String name = mark + "| " + item.getName();
            CategoryDTO categoryDTO = new CategoryDTO(item.getId(), name);
            categoryHierarchy.add(categoryDTO);
            if (item.getChildren().size() > 0) {
                addMarkHierarchy(item, numberDash + 1, categoryHierarchy);
            }
        }
    }

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveOrUpdate(Category category) {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentIds() == null ? "-" : parent.getAllParentIds();
            allParentIds += parent.getId() + "-";
            category.setAllParentIds(allParentIds);
        }
        return categoryRepository.save(category);
    }

    public List<Category> list() {
        return (List<Category>) categoryRepository.findAll();
    }

    public List<CategoryDTO> getCategoriesByHierarchy() {
        List<Category> categories = list();
        List<CategoryDTO> categoriesHierarchy = new ArrayList<>();
        int numberDash = 2;

        for (Category category : categories) {
            if (category.getParent() == null) {
                categoriesHierarchy.add(new CategoryDTO(category.getId(), category.getName()));
                addMarkHierarchy(category, numberDash, categoriesHierarchy);
            }
        }
        return categoriesHierarchy;
    }

    public Category get(Integer id) {
        return categoryRepository.findById(id).orElseGet(() -> null);
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElseGet(() -> null);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    public void changeStatus(Integer id, boolean status) {
        categoryRepository.changeStatus(id, status);
    }

}
