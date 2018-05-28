package com.example.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置适配器
 */
@Configuration
public class MyConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/");
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
        registry.addResourceHandler("/merchant/**").addResourceLocations("classpath:/merchant/");
    }
}
