package com.example.geolocation.services;

import com.example.geolocation.beans.DekResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import cryptolib.core.CryptoUtils;
import cryptolib.core.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class DemoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${kms.base-url}")
    private String KMS_BASE_URL;

    private final String DEK_RETRIEVAL_ENDPOINT = "keys/dek";
    private final String KEK_ROTATION_ENDPOINT = "keys/rotate";
    private final String MESSAGE = "PDS DEMO FOR ENCRYPTION AND DECRYPTION";
    private final String AES_ALGORITHM = "AES";

    private Logger logger = LoggerFactory.getLogger(DemoService.class);
    private ObjectMapper mapper = new ObjectMapper();

    public void demo() throws Exception {

        final HttpEntity<Object> requestEntity = new HttpEntity<>(new HttpHeaders());

        logger.info("Send DEK retrieval request to KMS");
        ResponseEntity<DekResponse> getDekResponse = restTemplate.exchange(KMS_BASE_URL + DEK_RETRIEVAL_ENDPOINT, HttpMethod.GET, requestEntity, DekResponse.class);

        logger.info("Get status [{}] from KMS", getDekResponse.getStatusCode().toString());

        DekResponse getDekResponseBody = getDekResponse.getBody();

        logger.info("Get response [{}] from KMS", mapper.writeValueAsString(getDekResponseBody));

        String dek = Objects.requireNonNull(getDekResponseBody).getData();

        logger.info("Get random nonce from Cryptolib");
        final byte[] randomNonce = CryptoUtils.getRandomNonce();

        logger.info("Decode base 64 the DEK");
        byte[] key = StringUtils.decodeBase64String(dek);

        logger.info("Build encryption key using DEK");
        SecretKey secretKey = new SecretKeySpec(key, AES_ALGORITHM);

        logger.info("Message to encrypt : [{}]", MESSAGE);
        logger.info("Encrypt message using DEK");

        final byte[] ciphertext = CryptoUtils.encryptAesGcm(MESSAGE.getBytes(StandardCharsets.UTF_8), secretKey, randomNonce);

        logger.info("Ciphertext in base 64 = {}", StringUtils.toBase64String(ciphertext));

        logger.info("Decrypting ciphertext using KEY");

        byte[] decryptedMessageBytes = CryptoUtils.decryptAesGcm(ciphertext, secretKey);

        String decryptedCipherText = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        logger.info("Decrypted ciphertext = {}", decryptedCipherText);

        logger.info("Rotating keys");

        restTemplate.exchange(KMS_BASE_URL + KEK_ROTATION_ENDPOINT, HttpMethod.POST, requestEntity, String.class);

        logger.info("Go check the KEK and DEK version on vault !");
        Thread.sleep(60000);

        logger.info("Re send DEK retrieval request to KMS");
        getDekResponse = restTemplate.exchange(KMS_BASE_URL + DEK_RETRIEVAL_ENDPOINT, HttpMethod.GET, requestEntity, DekResponse.class);

        logger.info("Get status [{}] from KMS", getDekResponse.getStatusCode().toString());

        getDekResponseBody = getDekResponse.getBody();

        logger.info("Get response [{}] from KMS", mapper.writeValueAsString(getDekResponseBody));

        dek = Objects.requireNonNull(getDekResponseBody).getData();

        logger.info("Decode base 64 the DEK");
        key = StringUtils.decodeBase64String(dek);

        logger.info("Build encryption key using DEK");
        secretKey = new SecretKeySpec(key, AES_ALGORITHM);

        logger.info("Decrypting ciphertext using KEY");

        decryptedMessageBytes = CryptoUtils.decryptAesGcm(ciphertext, secretKey);

        decryptedCipherText = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        logger.info("Decrypted ciphertext = {}", decryptedCipherText);

    }

}
