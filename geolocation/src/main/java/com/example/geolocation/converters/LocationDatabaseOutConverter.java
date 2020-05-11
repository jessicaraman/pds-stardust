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
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@ReadingConverter
public class LocationDatabaseOutConverter implements Converter<String, LocationEntity> {

    private final EncryptUtils encryptUtils;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    @Autowired
    public LocationDatabaseOutConverter(EncryptUtils encryptUtils, ObjectMapper objectMapper) {
        this.encryptUtils = encryptUtils;
        this.objectMapper = objectMapper;
        this.logger = LoggerFactory.getLogger(LocationDatabaseOutConverter.class);
    }

    @SneakyThrows
    @Override
    public LocationEntity convert(String source) {

        final SecretKey secretKey = encryptUtils.getDek();

        final byte[] decrypted = CryptoUtils.decryptAesGcm(
                StringUtils.decodeBase64String(source), secretKey
        );

        final String decryptedSource = new String(decrypted, StandardCharsets.UTF_8);

        logger.info("Converted [{}] to [{}]", source, decryptedSource);

        return objectMapper.readValue(decryptedSource, LocationEntity.class);

    }
}
