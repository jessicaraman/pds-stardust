package com.example.geolocation.services;

import com.example.geolocation.beans.DekResponse;
import cryptolib.core.CryptoUtils;
import cryptolib.core.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final String GET_DEK_URL = "https://pds.stardust:9980/api/kms/keys/dek";
    private final String ROTATE_KEY_URL = "https://pds.stardust:9980/api/kms/keys/rotate";
    private final String MESSAGE = "PDS DEMO FOR ENCRYPTION AND DECRYPTION";
    private final String AES_ALGORITHM = "AES";

    Logger logger = LoggerFactory.getLogger(DemoService.class);

    public void demo() throws Exception {

        final HttpEntity<Object> requestEntity = new HttpEntity<>(new HttpHeaders());

        logger.info("Send DEK retrieval request to KMS");
        ResponseEntity<DekResponse> getDekResponse = restTemplate.exchange(GET_DEK_URL, HttpMethod.GET, requestEntity, DekResponse.class);

        logger.info("Get status [{}] from KMS", getDekResponse.getStatusCode().toString());

        DekResponse getDekResponseBody = getDekResponse.getBody();

        logger.info("Get response [{}] from KMS", getDekResponseBody);

        String dek = Objects.requireNonNull(getDekResponseBody).getData();

        logger.info("Get random nonce from Cryptolib");
        final byte[] randomNonce = CryptoUtils.getRandomNonce();

        logger.info("Decode base 64 the DEK");
        byte[] key = StringUtils.decodeBase64String(dek);

        logger.info("Build encryption key using DEK");
        SecretKey secretKey = new SecretKeySpec(key, AES_ALGORITHM);

        logger.info("Message to encrypt : {}", MESSAGE);
        logger.info("Encrypt message using DEK");

        final byte[] ciphertext = CryptoUtils.encryptAesGcm(MESSAGE.getBytes(StandardCharsets.UTF_8), secretKey, randomNonce);

        logger.info("Ciphertext = {}", new String(ciphertext, StandardCharsets.UTF_8));

        logger.info("Decrypting ciphertext using KEY");

        byte[] decryptedMessageBytes = CryptoUtils.decryptAesGcm(ciphertext, secretKey);

        String decryptedCipherText = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        logger.info("Decrypted ciphertext = {}", decryptedCipherText);

        logger.info("Rotating keys");

        restTemplate.exchange(ROTATE_KEY_URL, HttpMethod.POST, requestEntity, String.class);

        logger.info("Re send DEK retrieval request to KMS");
        getDekResponse = restTemplate.exchange(GET_DEK_URL, HttpMethod.GET, requestEntity, DekResponse.class);

        logger.info("Get status [{}] from KMS", getDekResponse.getStatusCode().toString());

        getDekResponseBody = getDekResponse.getBody();

        logger.info("Get response [{}] from KMS", getDekResponseBody);

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
