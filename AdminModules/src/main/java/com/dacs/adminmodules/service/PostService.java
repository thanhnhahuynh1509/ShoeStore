package com.dacs.adminmodules.service;

import com.dacs.adminmodules.dto.CategoryDTO;
import com.dacs.adminmodules.repository.CategoryRepository;
import com.dacs.adminmodules.repository.PostRepository;
import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post saveOrUpdate(Post post) {
        return postRepository.save(post);
    }

    public List<Post> list() {
        return (List<Post>) postRepository.findAll();
    }

    public Post get(Integer id) {
        return postRepository.findById(id).orElseGet(() -> null);
    }

    public Post getByName(String title) {
        return postRepository.findByTitle(title);
    }

    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

}
