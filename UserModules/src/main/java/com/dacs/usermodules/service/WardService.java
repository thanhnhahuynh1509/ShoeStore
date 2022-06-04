package com.dacs.usermodules.service;

import com.dacs.entitymodules.District;
import com.dacs.entitymodules.Ward;
import com.dacs.usermodules.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardService {

    private final WardRepository wardRepository;

    public WardService(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    public List<Ward> listByDistrict(District district) {
        return wardRepository.findAllByDistrict(district);
    }

    public Ward get(String id) {
        return wardRepository.findById(id).orElseGet(() -> null);
    }
}
