package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.CategoryPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryPostRepository extends CrudRepository<CategoryPost, Integer> {

    @Query("FROM CategoryPost WHERE name = ?1")
    CategoryPost findByName(String name);

}
