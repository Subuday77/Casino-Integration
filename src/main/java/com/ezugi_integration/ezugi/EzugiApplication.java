package com.ezugi_integration.ezugi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EzugiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(EzugiApplication.class, args);
		System.out.println("Server started....................");
		
	}

}
