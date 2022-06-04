package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.dto.CategoryDTO;
import com.dacs.adminmodules.service.BrandService;
import com.dacs.adminmodules.service.CategoryService;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.adminmodules.utils.MessageRedirectUtils;
import com.dacs.entitymodules.Brand;
import com.dacs.entitymodules.Category;
import org.hibernate.sql.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;
    private final CategoryService categoryService;

    public BrandController(BrandService brandService, CategoryService categoryService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Brand> brands = brandService.list();
        model.addAttribute("brands", brands);
        return "/brand/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<CategoryDTO> categories = categoryService.getCategoriesByHierarchy();
        model.addAttribute("categories", categories);
        model.addAttribute("brand", new Brand());
        return "/brand/form";
    }

    @GetMapping("/update")
    public String update(Integer id, Model model, RedirectAttributes rd) {
        Brand brand = brandService.get(id);
        if(brand == null) {
            MessageRedirectUtils.setMessageRedirect("Không tìm thấy thương hiệu yêu cầu!", "danger", rd);
            return "redirect:/brands";
        }
        List<CategoryDTO> categories = categoryService.getCategoriesByHierarchy();
        model.addAttribute("categories", categories);
        model.addAttribute("brand", brand);
        return "/brand/form";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(Brand brand, MultipartFile fileUpload, RedirectAttributes rd) {
        if(brand.getId() == null)
            MessageRedirectUtils.setMessageRedirect("Thêm thương hiệu mới thành công!", "success", rd);
        else
            MessageRedirectUtils.setMessageRedirect("Cập nhật thương hiệu thành công!", "success", rd);

        if(!fileUpload.isEmpty()) {
            String fileName = AliasNameMaker.make(fileUpload.getOriginalFilename());
            brand.setImage(fileName);
            brandService.saveOrUpdate(brand);
            String dir = Brand.IMAGE_ROOT_DIR + "/" + brand.getId();
            FileUploadUtils.cleanDir(dir);
            FileUploadUtils.saveFile(dir, fileName, fileUpload);
        } else {
            if(brand.getImage().isEmpty())
                brand.setImage(null);
            brandService.saveOrUpdate(brand);
        }

        return "redirect:/brands";
    }
}
