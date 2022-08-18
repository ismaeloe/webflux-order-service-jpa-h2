package mx.com.ismaeloe.webflux_order_service_jpa.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mx.com.ismaeloe.webflux_order_service_jpa.dto.TransactionRequestDto;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.TransactionResponseDto;
import reactor.core.publisher.Mono;

@Service
public class CustomerClient {

	private final WebClient webClient;
	
	public CustomerClient( @Value("${customer.service.url}") String url ) {
		
		this.webClient = WebClient.create(url);	//WebClient.builder().baseUrl(url).build();
	}
	
	public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto txResquestDto) {
		
		return this.webClient.post()
						.uri("/transaction")
						.bodyValue(txResquestDto)	//payload
						.retrieve()	//Get Response
						.bodyToMono(TransactionResponseDto.class); //Set TransactionResponseDto
	}
	
}
