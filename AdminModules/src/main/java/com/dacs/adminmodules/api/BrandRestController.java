package com.dacs.adminmodules.api;

import com.dacs.adminmodules.dto.CategoryDTO;
import com.dacs.adminmodules.service.BrandService;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.entitymodules.Brand;
import com.dacs.entitymodules.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    private final BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/checkNameExists")
    public String checkNameExists(Integer id, String name) {
        Brand brand = brandService.getByName(name);
        if(brand != null && !brand.getId().equals(id))
            return "NOT OK";

        return "OK";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        Brand brand = brandService.get(id);
        if(brand == null)
            return "NOT OK";

        brandService.delete(id);
        String dir = Brand.IMAGE_ROOT_DIR + "/" + id;
        FileUploadUtils.cleanDir(dir);
        FileUploadUtils.deleteDir(dir);

        return "OK";
    }

    @GetMapping("/categories")
    public List<CategoryDTO> getCategoryByBrand(Integer id) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        Brand brand = brandService.get(id);
        if(brand != null) {
            brand.getCategories().forEach(m -> {
                Integer cId = m.getId();
                String name = m.getName();
                categoryDTOS.add(new CategoryDTO(cId, name));
            });
        }
        return categoryDTOS;
    }

}
