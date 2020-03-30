package com.pds.screstapi.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class HttpConfig {

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.key-store}")
    private String keyStorePath;

    @Bean
    public CloseableHttpClient httpClient() throws Throwable {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        keyStore.load(new FileInputStream(new File(keyStorePath)),
                keyStorePassword.toCharArray());

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                new SSLContextBuilder()
                        .loadKeyMaterial(keyStore, keyStorePassword.toCharArray()).build());

        return HttpClients.custom().setSSLSocketFactory(socketFactory).build();
    }

}
