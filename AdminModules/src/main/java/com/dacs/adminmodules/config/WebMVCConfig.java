package com.dacs.adminmodules.config;

import com.dacs.entitymodules.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        addPathToResourceHandler(Admin.IMAGE_ROOT_DIR, registry);
        addPathToResourceHandler(Category.IMAGE_ROOT_DIR, registry);
        addPathToResourceHandler(Brand.IMAGE_ROOT_DIR, registry);
        addPathToResourceHandler(Product.IMAGE_ROOT_DIR, registry);
        addPathToResourceHandler(Customer.IMAGE_ROOT_DIR, registry);
        addPathToResourceHandler(Post.IMAGE_ROOT_DIR, registry);
        addPathToResourceHandler("logo-images", registry);
    }

    private void addPathToResourceHandler(String dir, ResourceHandlerRegistry registry) {
        String absolutePath = new File(dir).getAbsolutePath();
        registry.addResourceHandler("/" + dir + "/**")
                .addResourceLocations("file:/" + absolutePath + "/");
    }
}
