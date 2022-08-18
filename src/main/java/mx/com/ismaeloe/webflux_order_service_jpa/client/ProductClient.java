package mx.com.ismaeloe.webflux_order_service_jpa.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mx.com.ismaeloe.webflux_order_service_jpa.dto.ProductDto;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

	private final WebClient webClient;
	
	public ProductClient( @Value("${product.service.url}") String url) {
		
		this.webClient = WebClient.builder()
									.baseUrl(url)
									.build(); 
	}
	
	public Mono<ProductDto> getProductById(final String id) {
		/*OK Testing
		ProductDto productDto = new ProductDto();
		productDto.setId(id);
		productDto.setDescription("ISMAEL PRODUCT");
		productDto.setPrice(10.00);
		
		return Mono.just(productDto );
		*/
		//*
		return this.webClient.get()
						.uri("/{id}" , id)
						.retrieve()
						.bodyToMono(ProductDto.class);
		//*/
	}
}
