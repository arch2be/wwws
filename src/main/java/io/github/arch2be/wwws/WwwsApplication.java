package io.github.arch2be.wwws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WwwsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WwwsApplication.class, args);
	}

}
