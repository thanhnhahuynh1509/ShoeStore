package com.dacs.usermodules.controller;

import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Product;
import com.dacs.usermodules.service.CategoryService;
import com.dacs.usermodules.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/p")
public class ProductController {

    private static final Integer LIMIT_RELEVANT_PRODUCT = 4;

    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{alias}")
    public String details(@PathVariable String alias, Model model) {
        Product product = productService.getByAlias(alias);
        List<Category> breadcrumb = categoryService.getCategoriesBreadcrumb(product.getCategory());
        List<Product> relevantProducts = productService.listProductsByCategoryAndExceptProduct(product.getCategory(), product.getId());

        if(product == null) {
            return "/error/404";
        }

        model.addAttribute("product", product);
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("relevantProducts", relevantProducts.stream().limit(LIMIT_RELEVANT_PRODUCT).collect(Collectors.toList()));
        return "/product/details";
    }
}
