package com.practo.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@SpringBootApplication(scanBasePackages = { "com.practo.urlshortener" })
@Configuration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
