package com.mediscreen.historymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *  Launch HistoryMicroservice Application
 */
@SpringBootApplication
@EnableSwagger2
public class HistoryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoryMicroserviceApplication.class, args);
	}

}
