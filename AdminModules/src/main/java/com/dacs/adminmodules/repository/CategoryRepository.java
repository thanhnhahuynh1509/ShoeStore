package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.name LIKE ?1")
    Optional<Category> findByName(String name);

    @Modifying
    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
    void changeStatus(Integer id, boolean status);
}
