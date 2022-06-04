package com.dacs.adminmodules.api;

import com.dacs.adminmodules.service.CategoryPostService;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.CategoryPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category-posts")
public class CategoryPostRestController {

    private final CategoryPostService categoryPostService;

    public CategoryPostRestController(CategoryPostService categoryPostService) {
        this.categoryPostService = categoryPostService;
    }

    @PostMapping("/checkNameExists")
    public String checkNameExists(Integer id, String name) {
        CategoryPost category = categoryPostService.getByName(name);
        if(category != null && !category.getId().equals(id))
            return "NOT OK";
        return "OK";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        CategoryPost category = categoryPostService.get(id);
        if(category == null)
            return "NOT OK";

        categoryPostService.delete(id);
        return "OK";
    }
}
