package com.dacs.adminmodules.repository;

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

    @Query("FROM Setting s WHERE s.type = ?1")
    List<Setting> findByType(SettingType type);

}
