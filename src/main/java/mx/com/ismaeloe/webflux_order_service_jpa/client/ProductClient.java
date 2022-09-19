package mx.com.ismaeloe.webflux_order_service_jpa.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.ProductDto;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

@Service
public class ProductClient {

	@Autowired
	WebClient.Builder webClientBuilder;

	private final WebClient webClient;

	//TODO https://rieckpil.de/spring-webclient-exchange-vs-retrieve-a-comparison/ 
	/*@Bean
	  public WebClient jsonPlaceholderWebClient(WebClient.Builder webClientBuilder) {
	
				TcpClient tcpClient = TcpClient.create()
			      .option(CONNECT_TIMEOUT_MILLIS, TIMEOUT_IN_SECONDS * 1000)
			      .doOnConnected(connection ->
			        connection
			          .addHandlerLast(new ReadTimeoutHandler(TIMEOUT_IN_SECONDS))
			          .addHandlerLast(new WriteTimeoutHandler(TIMEOUT_IN_SECONDS)));
			 
			    return webClientBuilder
			      .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
			      .baseUrl("https://jsonplaceholder.typicode.com/")
			      .build();
	*/
			
	public ProductClient( @Value("${product.service.url}") String url) {
		
		this.webClient =  WebClient.builder()   //this.webClientBuilder Caused by: java.lang.NullPointerException: null
									.baseUrl(url)
									.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
									.build(); 
	}
	
	public Mono<ProductDto> getProductById(final String id) {
		
		/* OK Testing
		 * ProductDto productDto = new ProductDto();
		 * productDto.setId(id);
		 * productDto.setDescription("ISMAEL PRODUCT");
		 * productDto.setPrice(10.00);
		 * return Mono.just(productDto );
		 */
		
		/* https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/
		 * https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/#using-the-exchange-method-to-retrieve-the-response
		 * The retrieve() method is the simplest way to get the response body.
		 *  However, If you want to have more control over the response, then you can use the exchange() method
		 *  which has access to the entire ClientResponse including all the headers and the body
		 */
		
		//OK
		return this.webClient
					.get()
					.uri("/{id}" , id)
					.retrieve()
					.bodyToMono(ProductDto.class);
		/*
		//TODO checar con exchangeToMono
		this.webClient.get()
						.uri("/{id}" , id)
						.header("Authorization", "Basic " + Base64Utils.encodeToString(("var username" + ":" + "var token").getBytes())) //UTF_8
						.exchangeToMono(  clientResponse -> clientResponse.bodyToMono(ProductDto.class) )
		
						//.exchange() //Mono<ClientResponse>  deprecated use exchangeToMono()  or  exchangeToFlux()
						//.flatMapMany(clientResponse -> clientResponse.bodyToFlux(GithubRepo.class));
		
		//TODO 
		.retrieve()
		  .onStatus(HttpStatus::is4xxClientError, response -> response.rawStatusCode() == 418 ? Mono.empty() : Mono.error(new RuntimeException("Error")))
		  .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new RuntimeException("Error")))
		  .bodyToMono(JsonNode.class)
		  */
	}
}
