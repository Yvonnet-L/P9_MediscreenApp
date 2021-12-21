package com.mediscreen.assessmentmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients

public class AssessmentMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentMicroserviceApplication.class, args);
	}

}
