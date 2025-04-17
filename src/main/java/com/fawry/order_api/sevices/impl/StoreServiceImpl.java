package com.fawry.order_api.sevices.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fawry.order_api.entity.StockConsumeRequestDTO;
import com.fawry.order_api.sevices.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final RestTemplate restTemplate;
    private final String checkProductAvailabilityUrl = "http://localhost:8085/api/stocks/availability";
    private final String consumeStockUrl = "http://localhost:8085/api/stocks/consume";

    @Override
    public boolean checkProductAvailability(Long productId, int quantity) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(checkProductAvailabilityUrl)
                    .queryParam("productId", productId)
                    .queryParam("quantity", quantity)
                    .toUriString();
            
            ResponseEntity<Long> response = restTemplate.getForEntity(url, Long.class);
            
            System.out.println(response.getBody());
            
            return response.getBody() ==1;
        } catch (RestClientException e) {
            log.error("Error checking product availability for product {} quantity {}", productId, quantity, e);
            return false;
        }
    }

    @Override
    public ResponseEntity<String> consumeStock(StockConsumeRequestDTO stockConsumeRequestDTO) {
        return restTemplate.postForEntity(
            consumeStockUrl, 
            stockConsumeRequestDTO, 
            String.class
        );
    }
}