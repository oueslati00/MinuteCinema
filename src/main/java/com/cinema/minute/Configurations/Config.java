package com.cinema.minute.Configurations;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@Configuration
public class Config {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // Single data size
        factory.setMaxFileSize(DataSize.parse("3", DataUnit.GIGABYTES));
        /// Total upload data size
        factory.setMaxRequestSize(DataSize.parse("3", DataUnit.GIGABYTES));
        return factory.createMultipartConfig();
    }
}
