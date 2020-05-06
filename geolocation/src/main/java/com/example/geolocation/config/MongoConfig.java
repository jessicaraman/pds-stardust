package com.example.geolocation.config;

import com.example.geolocation.converters.LocationDatabaseInConverter;
import com.example.geolocation.converters.LocationDatabaseOutConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {

    private final LocationDatabaseInConverter locationDatabaseInConverter;

    private final LocationDatabaseOutConverter locationDatabaseOutConverter;

    @Autowired
    public MongoConfig(LocationDatabaseInConverter locationDatabaseInConverter, LocationDatabaseOutConverter locationDatabaseOutConverter) {
        this.locationDatabaseInConverter = locationDatabaseInConverter;
        this.locationDatabaseOutConverter = locationDatabaseOutConverter;
    }

    @Bean
    public MongoCustomConversions customConversions() {

        List<Converter<?,?>> converters = new ArrayList<>();

        converters.add(locationDatabaseInConverter);
        converters.add(locationDatabaseOutConverter);

        return new MongoCustomConversions(converters);
    }

}
