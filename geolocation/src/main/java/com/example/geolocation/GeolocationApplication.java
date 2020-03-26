package com.example.geolocation;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
@EnableEncryptableProperties
public class GeolocationApplication {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static void main(String[] args) {
		SpringApplication.run(GeolocationApplication.class, args);
	}

}
