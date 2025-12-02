package com.companies.serivce_request.client;

import com.companies.serivce_request.client.dto.ServiceCatalogServiceResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Component
public class ServiceCatalogClient {

    private final RestClient restClient;

    public ServiceCatalogClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8083") // service-catalog base URL
                .build();
    }

    public ServiceCatalogServiceResponse getServiceById(UUID serviceId) {
        try {
            return restClient.get()
                    .uri("/api/services/{id}", serviceId)
                    .retrieve()
                    .body(ServiceCatalogServiceResponse.class);
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}
