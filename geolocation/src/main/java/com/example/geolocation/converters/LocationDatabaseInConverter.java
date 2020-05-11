package com.example.geolocation.converters;

import com.example.geolocation.entities.LocationEntity;
import com.example.geolocation.utils.EncryptUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import cryptolib.core.CryptoUtils;
import cryptolib.core.StringUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@WritingConverter
public class LocationDatabaseInConverter implements Converter<LocationEntity, String> {

    private final EncryptUtils encryptUtils;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    @Autowired
    public LocationDatabaseInConverter(EncryptUtils encryptUtils, ObjectMapper objectMapper) {
        this.encryptUtils = encryptUtils;
        this.objectMapper = objectMapper;
        this.logger = LoggerFactory.getLogger(LocationDatabaseInConverter.class);
    }

    @SneakyThrows
    @Override
    public String convert(LocationEntity source) {

        final String json = objectMapper.writeValueAsString(source);

        final SecretKey secretKey = encryptUtils.getDek();
        final byte[] randomNonce = CryptoUtils.getRandomNonce();

        final String encryptedSource = StringUtils.toBase64String(
                CryptoUtils.encryptAesGcm(
                        json.getBytes(StandardCharsets.UTF_8),
                        secretKey,
                        randomNonce
                )
        );

        logger.info("Converted [{}] to [{}]", objectMapper.writeValueAsString(source), encryptedSource);

        return encryptedSource;

    }
}
