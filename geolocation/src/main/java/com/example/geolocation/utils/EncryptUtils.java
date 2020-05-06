package com.example.geolocation.utils;

import com.example.geolocation.beans.DekResponse;
import cryptolib.core.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

import static cryptolib.constants.Constants.AES_ALGORITHM;

@Component
public class EncryptUtils {

    @Value("${kms.base-url}")
    private String KMS_BASE_URL;

    private final String DEK_RETRIEVAL_ENDPOINT = "keys/dek";

    private final RestTemplate restTemplate;

    @Autowired
    public EncryptUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SecretKey getDek() {

        final HttpEntity<Object> requestEntity = new HttpEntity<>(new HttpHeaders());

        ResponseEntity<DekResponse> response = restTemplate.exchange(KMS_BASE_URL + DEK_RETRIEVAL_ENDPOINT, HttpMethod.GET, requestEntity, DekResponse.class);

        DekResponse dekResponse = response.getBody();

        String dek = Objects.requireNonNull(dekResponse).getData();

        byte[] key = StringUtils.decodeBase64String(dek);

        return new SecretKeySpec(key, AES_ALGORITHM);
    }

}
