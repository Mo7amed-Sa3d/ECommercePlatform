package com.ecommerce.ecommerceplatform.client;

import com.ecommerce.ecommerceplatform.configuration.FedexConfig;
import com.ecommerce.ecommerceplatform.utility.FedexTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FedexClient {

    private final FedexConfig config;
    private final FedexTokenManager tokenManager;
    private final RestTemplate restTemplate;

    @Autowired
    public FedexClient(FedexConfig config, FedexTokenManager tokenManager, RestTemplate restTemplate) {
        this.config = config;
        this.tokenManager = tokenManager;
        this.restTemplate = restTemplate;
    }

    private HttpHeaders authHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenManager.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public Map createShipment(Map<String, Object> payload) {
        System.err.println(payload);
        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(payload, authHeaders());

        try {
            return restTemplate.postForEntity(
                    config.getBaseUrl() + config.getShipmentUrl()
                    , request
                    , Map.class
            ).getBody();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
