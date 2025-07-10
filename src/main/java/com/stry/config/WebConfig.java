package com.stry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path.pdf}")
    private String pdfUploadPath;

    @Value("${upload.path.image}")
    private String imageUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/pdf/**")
                .addResourceLocations("file:" + pdfUploadPath + "/");

        registry.addResourceHandler("/files/images/**")
                .addResourceLocations("file:" + imageUploadPath + "/");
    }
}