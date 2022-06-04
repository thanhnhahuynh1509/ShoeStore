package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.service.CategoryPostService;
import com.dacs.adminmodules.utils.MessageRedirectUtils;
import com.dacs.entitymodules.CategoryPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/category-posts")
public class CategoryPostController {

    private final CategoryPostService categoryPostService;

    public CategoryPostController(CategoryPostService categoryPostService) {
        this.categoryPostService = categoryPostService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<CategoryPost> categoryPosts = categoryPostService.listAll();
        model.addAttribute("categoryPosts", categoryPosts);
        return "/category-post/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categoryPost", new CategoryPost());
        return "/category-post/form";
    }

    @GetMapping("/update")
    public String update(Integer id, Model model) {
        CategoryPost categoryPost = categoryPostService.get(id);
        model.addAttribute("categoryPost", categoryPost);
        return "/category-post/form";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(CategoryPost categoryPost, RedirectAttributes rd) {
        categoryPostService.saveOrUpdate(categoryPost);

        if(categoryPost.getId() == null)
            MessageRedirectUtils.setMessageRedirect("Thêm thể loại mới thành công!", "success", rd);
        else
            MessageRedirectUtils.setMessageRedirect("Cập nhật thể loại thành công!", "success", rd);

        return "redirect:/category-posts";
    }

}
