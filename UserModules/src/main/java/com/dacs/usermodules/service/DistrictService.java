package com.dacs.usermodules.service;

import com.dacs.entitymodules.City;
import com.dacs.entitymodules.District;
import com.dacs.usermodules.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;

    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public List<District> listByCity(City city) {
        return districtRepository.findAllByCity(city);
    }

    public District get(String id) {
        return districtRepository.findById(id).orElseGet(() -> null);
    }

}
