package com.ecommerce.ecommerceplatform.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FedexConfig {

    @Value("${fedex.base-url}")
    private String baseUrl;

    @Value("${fedex.api-key}")
    private String apiKey;

    @Value("${fedex.secret-key}")
    private String secretKey;

    @Value("${fedex.account-number}")
    private String accountNumber;

    @Value("${fedex.shipmentUrl}")
    private String shipmentUrl;


}
