package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query("FROM Post WHERE title = ?1")
    Post findByTitle(String title);

}
