package com.order.order_model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${inventory.url:http://localhost:9002}")
    private String inventoryUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(inventoryUrl)
                .build();
    }
}
