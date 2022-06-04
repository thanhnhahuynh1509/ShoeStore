package com.dacs.adminmodules.api;

import com.dacs.adminmodules.service.CategoryService;
import com.dacs.adminmodules.service.PostService;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/checkNameExists")
    public String checkNameExists(Integer id, String name) {
        Post post = postService.getByName(name);
        if(post != null && !post.getId().equals(id))
            return "NOT OK";
        return "OK";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        Post Post = postService.get(id);
        if(Post == null)
            return "NOT OK";

        postService.delete(id);
        String dir = Post.IMAGE_ROOT_DIR + "/" + id;
        FileUploadUtils.cleanDir(dir);
        FileUploadUtils.deleteDir(dir);

        return "OK";
    }

}
