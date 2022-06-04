package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Setting;
import com.dacs.entitymodules.enumType.SettingType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends CrudRepository<Setting, String> {
    @Query("FROM Setting s WHERE s.key = ?1")
    Setting findByKey(String key);

}
