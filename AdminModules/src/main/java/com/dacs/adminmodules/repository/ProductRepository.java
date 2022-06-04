package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 or p.category.allParentIds LIKE CONCAT('%-', ?1, '-%') ")
    List<Product> findByCategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1")
    Optional<Product> findByName(String name);

    @Modifying
    @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
    void changeStatus(Integer id, boolean status);

}
