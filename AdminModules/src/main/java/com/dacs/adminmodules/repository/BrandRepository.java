package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand b WHERE b.name LIKE ?1")
    Optional<Brand> findByName(String name);

}
