package com.vvs.murator;

import com.vvs.murator.auth.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({
		CorsConfig.class
})
public class MuratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuratorApplication.class, args);
	}
}
