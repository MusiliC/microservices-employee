package dev.musili.demo_configuration_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class DemoConfigurationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoConfigurationServerApplication.class, args);
	}

}
