package mx.com.ismaeloe.webflux_order_service_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebfluxOrderServiceJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxOrderServiceJpaApplication.class, args);
	}

	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
}
