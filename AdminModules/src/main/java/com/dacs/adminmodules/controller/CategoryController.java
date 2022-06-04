package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.service.CategoryService;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.adminmodules.utils.MessageRedirectUtils;
import com.dacs.entitymodules.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpClient;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("categories", categories);
        return "/category/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getCategoriesByHierarchy());
        return "/category/form";
    }

    @GetMapping("/update")
    public String update(Integer id, Model model, RedirectAttributes rd) {
        Category category = categoryService.get(id);
        if(category == null) {
            MessageRedirectUtils.setMessageRedirect("Không tìm thấy thể loại yêu cầu!", "danger", rd);
            return "redirect:/categories";
        }
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryService.getCategoriesByHierarchy());
        return "/category/form";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(Category category, MultipartFile fileUpload, RedirectAttributes rd) {
        if(category.getId() == null)
            MessageRedirectUtils.setMessageRedirect("Thêm thể loại mới thành công!", "success", rd);
        else
            MessageRedirectUtils.setMessageRedirect("Cập nhật thể loại thành công!", "success", rd);

        String alias = AliasNameMaker.make(category.getName());
        if(!fileUpload.isEmpty()) {
            String fileName = AliasNameMaker.make(fileUpload.getOriginalFilename());
            category.setImage(fileName);
            category.setAlias(alias);

            category = categoryService.saveOrUpdate(category);
            String dir = Category.IMAGE_ROOT_DIR + "/" + category.getId();
            FileUploadUtils.cleanDir(dir);
            FileUploadUtils.saveFile(dir, fileName, fileUpload);
        } else {
            if(category.getImage().isEmpty())
                category.setImage(null);
            category.setAlias(alias);
            categoryService.saveOrUpdate(category);
        }
        return "redirect:/categories";
    }

}
