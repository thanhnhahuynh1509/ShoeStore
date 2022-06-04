package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.service.BrandService;
import com.dacs.adminmodules.service.CategoryService;
import com.dacs.adminmodules.service.ProductService;
import com.dacs.adminmodules.utils.AliasNameMaker;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.adminmodules.utils.MessageRedirectUtils;
import com.dacs.entitymodules.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, BrandService brandService, CategoryService categoryService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String index(@RequestParam(required = false, name = "categoryId") Integer categoryId, Model model) {
        List<Product> products = productService.list();
        if(categoryId != null && categoryId > 0) {
            products = productService.listByCategoryId(categoryId);
        }
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getCategoriesByHierarchy());
        return "/product/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("brands", brandService.list());
        model.addAttribute("product", new Product());
        return "/product/form";
    }

    @GetMapping("/update")
    public String update(Integer id, Model model, RedirectAttributes rd) {
        Product product = productService.get(id);
        if (product == null) {
            MessageRedirectUtils.setMessageRedirect("Không tìm thấy sản phẩm yêu cầu", "danger", rd);
            return "redirect:/products";
        }
        model.addAttribute("brands", brandService.list());
        model.addAttribute("product", product);
        return "/product/form";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(Product product, MultipartFile fileUpload,
                               MultipartFile[] extraFileUpload,
                               Integer[] extraImageIds,
                               String[] extraImageNames,
                               Integer[] detailIds,
                               String[] detailNames,
                               String[] detailValues,
                               RedirectAttributes rd) {
        if (product.getId() == null)
            MessageRedirectUtils.setMessageRedirect("Thêm sản phẩm mới thành công!", "success", rd);
        else
            MessageRedirectUtils.setMessageRedirect("Cập nhật sản phẩm thành công!", "success", rd);

        setProductMainImage(product, fileUpload);
        setProductExtraImage(product, extraImageIds, extraImageNames);
        setProductDetail(product, detailIds, detailNames, detailValues);
        saveProductAndImages(product, fileUpload, extraFileUpload);

        return "redirect:/products";
    }

    @GetMapping("/details")
    public String details(Integer id, Model model, RedirectAttributes rd) {
        Product product = productService.get(id);
        if (product == null) {
            MessageRedirectUtils.setMessageRedirect("Không tìm thấy sản phẩm yêu cầu", "danger", rd);
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "/product/details";
    }

    private void setProductMainImage(Product product, MultipartFile fileUpload) {
        if (!fileUpload.isEmpty()) {
            String fileName = AliasNameMaker.make(fileUpload.getOriginalFilename());
            product.setMainImage(fileName);
        } else {
            if (product.getMainImage().isEmpty())
                product.setMainImage(null);
        }
    }

    private void setProductExtraImage(Product product, Integer[] extraImageIds, String[] extraImageNames) {
        if (extraImageNames.length <= 0)
            return;
        int length = extraImageNames.length;
        for (int i = 0; i < length; i++) {
            if(!extraImageNames[i].isEmpty()) {
                product.addExtraImage(extraImageIds[i], AliasNameMaker.make(extraImageNames[i]));
            }
        }
        deleteExtraImageWereRemovedOnForm(product);
    }

    private void deleteExtraImageWereRemovedOnForm(Product product) {
        if(product.getId() != null) {
            String extraDir = Product.IMAGE_ROOT_DIR + "/" + product.getId() + "/extra";
            try {
                Files.list(Paths.get(extraDir)).forEach(file -> {
                    String fileName = file.getFileName().toString();
                    if(!product.containsImageName(fileName)) {
                        try {
                            Files.delete(file);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void saveProductAndImages(Product product, MultipartFile fileUpload, MultipartFile[] extraFileUpload) {
        product = productService.saveOrUpdate(product);
        if (!fileUpload.isEmpty()) {
            String fileName = AliasNameMaker.make(fileUpload.getOriginalFilename());
            String dir = Product.IMAGE_ROOT_DIR + "/" + product.getId();
            FileUploadUtils.cleanDir(dir);
            FileUploadUtils.saveFile(dir, fileName, fileUpload);
        }

        for (MultipartFile file : extraFileUpload) {
            if (!file.isEmpty()) {
                String fileName = AliasNameMaker.make(file.getOriginalFilename());
                String extraDir = Product.IMAGE_ROOT_DIR + "/" + product.getId() + "/extra";
                FileUploadUtils.saveFile(extraDir, fileName, file);
            }
        }
    }

    private void setProductDetail(Product product, Integer[] detailIds, String[] detailNames, String[] detailValues) {
        if(detailNames == null || detailNames.length <= 0)
            return;
        int length = detailNames.length;
        for(int i = 0; i < length; i++) {
            if(!detailNames[i].isEmpty()) {
                product.addProductDetail(detailIds[i], detailNames[i], detailValues[i]);
            }
        }
    }

}
