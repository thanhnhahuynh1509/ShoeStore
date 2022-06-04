package com.dacs.usermodules.config;

import com.dacs.entitymodules.Category;
import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Product;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeImage(Category.IMAGE_ROOT_DIR, registry);
        exposeImage(Product.IMAGE_ROOT_DIR, registry);
        exposeImage(Customer.IMAGE_ROOT_DIR, registry);
    }

    private void exposeImage(String path, ResourceHandlerRegistry registry) {
        String absolutePath = new File(path).getAbsolutePath();
        registry.addResourceHandler("/" + path + "/**")
                .addResourceLocations("file:/" + absolutePath + "/");
    }
}
