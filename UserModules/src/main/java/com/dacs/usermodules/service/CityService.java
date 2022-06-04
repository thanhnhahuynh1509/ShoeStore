package com.dacs.usermodules.service;

import com.dacs.entitymodules.City;
import com.dacs.usermodules.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> list() {
        return cityRepository.findAllByOrderByNameAsc();
    }

    public City get(String id) {
        return cityRepository.findById(id).orElseGet(() -> null);
    }

}
