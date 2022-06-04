package com.dacs.usermodules.service;

import com.dacs.entitymodules.Setting;
import com.dacs.entitymodules.enumType.SettingType;
import com.dacs.usermodules.repository.SettingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public Setting getByKey(String key) {
        return settingRepository.findByKey(key);
    }
}
