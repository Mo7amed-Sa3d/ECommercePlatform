package com.ecommerce.ecommerceplatform.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final String PRODUCT_IMAGE_PATH = "file:uploads/products/";
    private final String PRODUCT_IMAGE_HANDLER = "/images/products/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PRODUCT_IMAGE_HANDLER)
                .addResourceLocations(PRODUCT_IMAGE_PATH)
                .setCachePeriod(3600) // optional: cache 1 hour
                .resourceChain(true);
    }
}
