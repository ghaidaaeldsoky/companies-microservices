package com.companies.serivce_request.client;

import com.companies.serivce_request.api.ApiResponse;
import com.companies.serivce_request.client.dto.ServiceCatalogServiceResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
            String token = getCurrentJwtToken();
            ApiResponse<ServiceCatalogServiceResponse> response = restClient.get()
                    .uri("/api/services/{id}", serviceId)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<ServiceCatalogServiceResponse>>() {});

            if (response == null) {
                return null;
            }

            return response.getData();  // this is your ServiceCatalogServiceResponse
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }

    private String getCurrentJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }

        throw new IllegalStateException("No JWT token found in SecurityContext – is this endpoint protected?");
    }
}
