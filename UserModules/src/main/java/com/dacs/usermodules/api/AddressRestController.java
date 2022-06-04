package com.dacs.usermodules.api;

import com.dacs.entitymodules.City;
import com.dacs.entitymodules.District;
import com.dacs.entitymodules.Ward;
import com.dacs.usermodules.dto.DistrictDTO;
import com.dacs.usermodules.dto.WardDTO;
import com.dacs.usermodules.service.CityService;
import com.dacs.usermodules.service.DistrictService;
import com.dacs.usermodules.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressRestController {

    private final DistrictService districtService;
    private final WardService wardService;

    public AddressRestController(DistrictService districtService, WardService wardService) {
        this.districtService = districtService;
        this.wardService = wardService;
    }

    @GetMapping("/{cityId}/districts")
    public List<DistrictDTO> districtDTOS(@PathVariable String cityId) {
        List<District> districts = districtService.listByCity(new City(cityId));
        List<DistrictDTO> districtDTOS = new ArrayList<>();
        districts.forEach(m -> {
            districtDTOS.add(new DistrictDTO(m.getId(), m.getName()));
        });
        return districtDTOS;
    }

    @GetMapping("/{districtId}/wards")
    public List<WardDTO> wardDTOS(@PathVariable String districtId) {
        List<Ward> wards = wardService.listByDistrict(new District(districtId));
        List<WardDTO> wardDTOS = new ArrayList<>();
        wards.forEach(m -> {
            wardDTOS.add(new WardDTO(m.getId(), m.getName()));
        });
        return wardDTOS;
    }
}
