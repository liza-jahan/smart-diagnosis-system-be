package com.liza.smart.diagnosis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liza.smart.diagnosis.dto.BrainStockDetectionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor               //create constructor
public class DiagonosisApiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public boolean stockDetectionRequest(BrainStockDetectionRequest request) {
        String apiUrl = "http://127.0.0.1:8000/api/stockDetection/";

        // Create the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Convert the PatientRequest object to a JSON string
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting object to JSON", e);
        }

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request and expect a boolean response
        ResponseEntity<Object> response = restTemplate.postForEntity(apiUrl, requestEntity, Object.class);

        // Get the boolean result from the response
        boolean result = (boolean) response.getBody();

        // Print the result (you can handle it according to your application logic)
        System.out.println("API Response: " + result);

        return result;
    }

}
