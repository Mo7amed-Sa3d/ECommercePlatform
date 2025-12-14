package com.ecommerce.ecommerceplatform.configuration;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;



@Configuration
public class HttpClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        // HttpClient 5.x handles gzip automatically by default
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(30000);

        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }



}