package com.dacs.adminmodules.api;

import com.dacs.adminmodules.service.CategoryService;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.entitymodules.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/checkNameExists")
    public String checkNameExists(Integer id, String name) {
        Category category = categoryService.getByName(name);
        if(category != null && !category.getId().equals(id))
            return "NOT OK";
        return "OK";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        Category category = categoryService.get(id);
        if(category == null)
            return "NOT OK";

        categoryService.delete(id);
        String dir = Category.IMAGE_ROOT_DIR + "/" + id;
        FileUploadUtils.cleanDir(dir);
        FileUploadUtils.deleteDir(dir);

        return "OK";
    }

    @GetMapping("/changeStatus")
    public boolean changeStatus(Integer id, boolean status) {
        categoryService.changeStatus(id, status);
        return !status;
    }
}
