package com.aws.mb.awsmb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AwsMbApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsMbApplication.class, args);
	}

}
