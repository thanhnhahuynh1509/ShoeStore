package com.dacs.usermodules.service;

import com.dacs.entitymodules.PayPalOrderResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class PayPalService {
    private static final String GET_ORDER_API = "/v2/checkout/orders/";

    private final SettingService settingService;

    public PayPalService(SettingService settingService) {
        this.settingService = settingService;
    }

    public boolean validateOrder(String orderId) {
        String baseUrl = settingService.getByKey("PAYPAL_URL_API").getValue();
        String requestUrl = baseUrl + GET_ORDER_API + orderId;
        String clientId = settingService.getByKey("PAYPAL_CLIENT_ID").getValue();
        String clientSecret = settingService.getByKey("PAYPAL_CLIENT_SECRET").getValue();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language", "en_US");
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PayPalOrderResponse> response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, PayPalOrderResponse.class);

        HttpStatus statusCode = response.getStatusCode();

        if(!statusCode.equals(HttpStatus.OK))
            throw new RuntimeException("Exception With Paypal");

        PayPalOrderResponse orderResponse = response.getBody();

        return orderResponse.validate(orderId);
    }
}
