package com.dacs.adminmodules.category;

import com.dacs.adminmodules.dto.CategoryDTO;
import com.dacs.adminmodules.repository.CategoryRepository;
import com.dacs.entitymodules.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testHierarchy() {
        getCategoriesByHierarchy().forEach(m -> {
            System.out.println(m.getName());
        });
    }

    public List<CategoryDTO> getCategoriesByHierarchy() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        List<CategoryDTO> categoriesHierarchy = new ArrayList<>();
        int numberDash = 2;

        for(Category category : categories) {
            if(category.getParent() == null) {
                categoriesHierarchy.add(new CategoryDTO(category.getId(), category.getName()));
                addMarkHierarchy(category, numberDash, categoriesHierarchy);
            }
        }
        return categoriesHierarchy;
    }

    private void addMarkHierarchy(Category category, int numberDash, List<CategoryDTO> categoryHierarchy) {
        Set<Category> categories = category.getChildren();
        StringBuilder mark = new StringBuilder();
        mark.append("..".repeat(numberDash));

        for(Category item : categories) {
            String name =  mark + "| " + item.getName();
            CategoryDTO categoryDTO = new CategoryDTO(item.getId(), name);
            categoryHierarchy.add(categoryDTO);
            if (item.getChildren().size() > 0) {
                addMarkHierarchy(item, numberDash + 1, categoryHierarchy);
            }
        }
    }
}
