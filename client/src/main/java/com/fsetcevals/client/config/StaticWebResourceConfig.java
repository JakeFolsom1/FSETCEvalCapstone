package com.fsetcevals.client.config;


import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;


public class StaticWebResourceConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/", "classpath:/static/","classpath:/public/css/","classpath:/public/js/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
