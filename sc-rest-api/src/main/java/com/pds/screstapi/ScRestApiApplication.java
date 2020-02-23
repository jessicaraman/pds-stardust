package com.pds.screstapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ScRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScRestApiApplication.class, args);
	}

}
