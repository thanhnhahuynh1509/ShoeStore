package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("FROM Category WHERE products.size > 0")
    List<Category> findAllCategoriesHaveProducts();

}
