package mx.com.ismaeloe.webflux_order_service_jpa.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mx.com.ismaeloe.webflux_order_service_jpa.dto.TransactionRequestDto;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.TransactionResponseDto;
import reactor.core.publisher.Mono;

//TODO @RefreshScope
@Service
public class CustomerClient {

	private final WebClient webClient;
	
	public CustomerClient( @Value("${customer.service.url}") String url ) {
		
		/* TODO implemntation
		ClientHttpConnector connector = new ReactorClientHttpConnector();
		*/
		
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
