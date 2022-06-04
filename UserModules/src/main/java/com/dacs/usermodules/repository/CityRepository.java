package com.dacs.usermodules.repository;

import com.dacs.entitymodules.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, String> {

    @Query("FROM City ORDER BY name ASC")
    List<City> findAllByOrderByNameAsc();
}
