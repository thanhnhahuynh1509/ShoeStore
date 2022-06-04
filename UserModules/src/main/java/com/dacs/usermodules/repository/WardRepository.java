package com.dacs.usermodules.repository;

import com.dacs.entitymodules.District;
import com.dacs.entitymodules.Ward;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends CrudRepository<Ward, String> {

    @Query("FROM Ward w Where w.district = ?1 ORDER BY w.name ASC")
    List<Ward> findAllByDistrict(District district);

}
