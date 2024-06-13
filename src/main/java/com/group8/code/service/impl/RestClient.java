package com.group8.code.service.impl;

import com.group8.code.config.RestTemplateConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public RestClient(RestTemplate restTemplate, RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplate;
        this.baseUrl = restTemplateConfig.getBaseUrl();
    }

//    public <T> T get(String endpoint, Class<T> responseType) {
//        return restTemplate.getForObject(baseUrl + endpoint, responseType);
//    }
    public <T> ResponseEntity<T> get(String endpoint, Class<T> responseType) {
        return restTemplate.getForEntity(baseUrl + endpoint, responseType);
    }

    public <T> ResponseEntity<T> post(String endpoint, Object request, Class<T> responseType) {
        return restTemplate.postForEntity(baseUrl + endpoint, request, responseType);
    }

    public void put(String endpoint, Object request) {
        restTemplate.put(baseUrl + endpoint, request);
    }

    public void delete(String endpoint) {
        restTemplate.delete(baseUrl + endpoint);
    }
}
