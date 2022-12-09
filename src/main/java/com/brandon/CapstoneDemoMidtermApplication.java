package com.brandon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(name="swaggerui", scheme="Bearer", type = SecuritySchemeType.APIKEY, in=SecuritySchemeIn.HEADER)
@EnableOAuth2Client
@EnableAutoConfiguration
public class CapstoneDemoMidtermApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneDemoMidtermApplication.class, args);
	}

}
