package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.service.AdminService;
import com.dacs.adminmodules.service.CategoryPostService;
import com.dacs.adminmodules.service.PostService;
import com.dacs.adminmodules.utils.AdminLoginUtils;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.adminmodules.utils.MessageRedirectUtils;
import com.dacs.entitymodules.Admin;
import com.dacs.entitymodules.CategoryPost;
import com.dacs.entitymodules.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CategoryPostService categoryPostService;
    private final AdminService adminService;

    public PostController(PostService postService, CategoryPostService categoryPostService, AdminService adminService) {
        this.postService = postService;
        this.categoryPostService = categoryPostService;
        this.adminService = adminService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Post> posts = postService.list();
        List<CategoryPost> categoryPosts = categoryPostService.listAll();
        model.addAttribute("posts", posts);
        model.addAttribute("categoryPosts", categoryPosts);
        return "/post/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<CategoryPost> categoryPosts = categoryPostService.listAll();
        model.addAttribute("post", new Post());
        model.addAttribute("categoryPosts", categoryPosts);
        return "/post/form";
    }

    @GetMapping("/update")
    public String update(Integer id, Model model) {
        List<CategoryPost> categoryPosts = categoryPostService.listAll();
        model.addAttribute("post", postService.get(id));
        model.addAttribute("categoryPosts", categoryPosts);
        return "/post/form";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(Post post, MultipartFile fileUpload, HttpServletRequest request, RedirectAttributes rd) {
        String email = AdminLoginUtils.getEmail(request);
        Admin admin = adminService.getByEmail(email);
        post.setAdmin(admin);
        if(!fileUpload.isEmpty()) {
            String fileName = AliasNameMaker.make(fileUpload.getOriginalFilename());
            post.setMainImage(fileName);
            post = postService.saveOrUpdate(post);
            String dir = Post.IMAGE_ROOT_DIR + "/" + post.getId();
            FileUploadUtils.cleanDir(dir);
            FileUploadUtils.saveFile(dir, fileName, fileUpload);
        } else {
            if(post.getMainImage() == null || post.getMainImage().isEmpty()) {
                post.setMainImage(null);
            }
            postService.saveOrUpdate(post);
        }

        if(post.getId() == null)
            MessageRedirectUtils.setMessageRedirect("Thêm bài viết mới thành công!", "success", rd);
        else
            MessageRedirectUtils.setMessageRedirect("Cập nhật bài viết thành công!", "success", rd);

        return "redirect:/posts";
    }

}
