package com.dacs.usermodules.controller;

import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Product;
import com.dacs.usermodules.service.CategoryService;
import com.dacs.usermodules.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/s")
public class ShopController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public ShopController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "c", required = false, defaultValue = "") String categoryAlias,
                        @RequestParam(name = "k", required = false, defaultValue = "") String keyword,
                        @RequestParam(name="price", required= false) Integer[] prices) {


        List<Product> products = new ArrayList<>();

        if(categoryAlias != null && !categoryAlias.isEmpty()) {
            products = productService.listProductsByCategoryAlias(categoryAlias);
        } else {
            products = productService.listNewProducts();
        }
        products = products.stream()
                .filter(m -> m.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if(prices != null) {
            Arrays.sort(prices);
            products = products.stream()
                    .filter(m -> m.getPrice() >= prices[0] && m.getPrice() <= prices[1])
                    .collect(Collectors.toList());

            model.addAttribute("minPrice", prices[0]);
            model.addAttribute("maxPrice", prices[1]);
        }

        List<Category> categoriesForIterator = categoryService.listCategoriesHaveProducts();

        Set<Category> categories = new HashSet<>();

        for(Product product : products) {
            if(categoriesForIterator.contains(product.getCategory()))
                categories.add(product.getCategory());
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryAlias", categoryAlias);

        return "/shop/index";
    }
}
