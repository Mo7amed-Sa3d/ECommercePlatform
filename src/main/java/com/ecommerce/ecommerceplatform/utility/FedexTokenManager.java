package com.ecommerce.ecommerceplatform.utility;

import com.ecommerce.ecommerceplatform.configuration.FedexConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FedexTokenManager {

    private final FedexConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    private String token;
    private Instant expiry;

    public synchronized String getToken() {

        if (token != null && expiry.isAfter(Instant.now())) {
            return token;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body =
                "grant_type=client_credentials" +
                        "&client_id=" + config.getApiKey() +
                        "&client_secret=" + config.getSecretKey();

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        config.getBaseUrl() + "/oauth/token",
                        request,
                        Map.class
                );

        token = response.getBody().get("access_token").toString();
        int expiresIn = (int) response.getBody().get("expires_in");

        expiry = Instant.now().plusSeconds(expiresIn - 60);

        return token;
    }
}
