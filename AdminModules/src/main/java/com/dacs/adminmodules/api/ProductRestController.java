package com.dacs.adminmodules.api;

import com.dacs.adminmodules.service.ProductService;
import com.dacs.adminmodules.utils.FileUploadUtils;
import com.dacs.entitymodules.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/changeStatus")
    public boolean changeStatus(Integer id, boolean status) {
        productService.changeStatus(id, status);
        return !status;
    }

    @PostMapping("/checkNameExists")
    public String checkNameExists(Integer id, String name) {
        Product product = productService.getByName(name);
        if(product != null && !product.getId().equals(id))
            return "NOT OK";
        return "OK";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        Product product = productService.get(id);
        if(product == null)
            return "NOT OK";
        String dir = Product.IMAGE_ROOT_DIR + "/" + id;
        String extraDir = dir + "/extra";
        FileUploadUtils.cleanDir(extraDir);
        FileUploadUtils.deleteDir(extraDir);
        FileUploadUtils.cleanDir(dir);
        FileUploadUtils.deleteDir(dir);
        productService.delete(id);
        return "OK";
    }

}
