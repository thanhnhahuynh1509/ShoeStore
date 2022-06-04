package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("FROM Product ORDER BY id DESC")
    List<Product> findAllByOrderIdDesc();

    @Query("FROM Product WHERE alias = ?1")
    Product findByAlias(String alias);

    @Query("FROM Product WHERE category = ?1 AND id <> ?2")
    List<Product> findAllByCategoryAndExceptProduct(Category category, Integer productId);

    @Query("FROM Product WHERE category.alias = ?1")
    List<Product> findAllByCategoryAlias(String alias);
}
