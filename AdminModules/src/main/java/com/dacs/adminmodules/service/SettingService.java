package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.SettingRepository;
import com.dacs.entitymodules.Setting;
import com.dacs.entitymodules.enumType.SettingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public Setting save(Setting setting) {
        return settingRepository.save(setting);
    }

    public Setting getByKey(String key) {
        return settingRepository.findByKey(key);
    }

    public List<Setting> listByType(SettingType type) {
        return settingRepository.findByType(type);
    }

    public List<Setting> listAll() {
        return (List<Setting>) settingRepository.findAll();
    }
}
