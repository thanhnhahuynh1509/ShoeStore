package com.dacs.usermodules.repository;

import com.dacs.entitymodules.City;
import com.dacs.entitymodules.District;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends CrudRepository<District, String> {

    @Query("FROM District d WHERE d.city = ?1 ORDER BY d.name ASC")
    List<District> findAllByCity(City city);

}
