package com.example.geolocation.services;

import com.example.geolocation.beans.DekResponse;
import com.example.geolocation.entities.LocationEntity;
import com.example.geolocation.entities.LocationHistoryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import cryptolib.core.CryptoUtils;
import cryptolib.core.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DemoService extends AbstractRestService {

    @Value("${kms.base-url}")
    private String KMS_BASE_URL;

    private final LocationHistoryService locationHistoryService;

    private final String DEK_RETRIEVAL_ENDPOINT = "keys/dek";
    private final String KEK_ROTATION_ENDPOINT = "keys/rotate";
    private final String MESSAGE = "PDS DEMO FOR ENCRYPTION AND DECRYPTION";
    private final String AES_ALGORITHM = "AES";

    private final int LOCATION_HISTORY_SIZE = 5;
    private final String LOCATION_HISTORY_ID = "demo";

    private Logger logger = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    public DemoService(RestTemplate restTemplate, ObjectMapper mapper, LocationHistoryService locationHistoryService) {
        super(restTemplate, mapper);
        this.locationHistoryService = locationHistoryService;
    }

    public void automatedDemo() throws Exception {

        final HttpEntity<Object> requestEntity = new HttpEntity<>(new HttpHeaders());

        logger.info("Send DEK retrieval request to KMS");

        String url = KMS_BASE_URL + DEK_RETRIEVAL_ENDPOINT;
        ResponseEntity<DekResponse> getDekResponse = get(url, requestEntity, DekResponse.class);

        DekResponse getDekResponseBody = getDekResponse.getBody();

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

        url = KMS_BASE_URL + KEK_ROTATION_ENDPOINT;

        post(url, requestEntity, String.class);

        logger.info("Go check the KEK and DEK version on vault !");
        Thread.sleep(60000);

        url = KMS_BASE_URL + DEK_RETRIEVAL_ENDPOINT;

        logger.info("Re send DEK retrieval request to KMS");
        getDekResponse = get(url, requestEntity, DekResponse.class);

        getDekResponseBody = getDekResponse.getBody();

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

    public Optional<LocationHistoryEntity> automatedDatabaseDemo() throws Exception {

        logger.info("Clearing [locationHistoryEntity] collection !");
        locationHistoryService.clearCollection();

        logger.info("Go check that the collection is empty");
        Thread.sleep(15000);

        LocationHistoryEntity locationHistory = new LocationHistoryEntity();
        locationHistory.setId(LOCATION_HISTORY_ID);

        List<LocationEntity> locations = new ArrayList<>();

        logger.info("Generating an location history !");

        for (int i = 0; i < LOCATION_HISTORY_SIZE; i++) {
            locations.add(new LocationEntity(i, -i, LocalDateTime.now().toString()));
        }

        locationHistory.setLocations(locations);

        logger.info("Saving the location history");

        locationHistoryService.save(locationHistory);

        logger.info("Go check that the collection is not empty and encrypted");
        Thread.sleep(15000);

        logger.info("Finding the location history in database");
        return locationHistoryService.findById(LOCATION_HISTORY_ID);

    }

    @Override
    protected String getServiceName() {
        return "KMS";
    }
}
