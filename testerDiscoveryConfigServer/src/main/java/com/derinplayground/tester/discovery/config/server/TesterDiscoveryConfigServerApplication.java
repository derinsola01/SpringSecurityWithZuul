package com.derinplayground.tester.discovery.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TesterDiscoveryConfigServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TesterDiscoveryConfigServerApplication.class, args);
	}

}
