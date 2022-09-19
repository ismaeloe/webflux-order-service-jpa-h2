# webflux-Order Service 
Spring WebFlux Reactor Order Service with JPA H2

	/* https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/
	 * https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/#using-the-exchange-method-to-retrieve-the-response
	 * The retrieve() method is the simplest way to get the response body.
	 *  However, If you want to have more control over the response, then you can use the exchange() method
	 *  which has access to the entire ClientResponse including all the headers and the body
	 */

/*
 * https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/#using-the-exchange-method-to-retrieve-the-response
 * https://reflectoring.io/bean-validation-with-spring-boot/ 	//Good with @WebMvcTest(controllers = ValidateRequestBodyController.class)
 * https://github.com/thombergs/code-examples/tree/master/spring-boot/validation
 * https://github.com/thombergs/code-examples/tree/master/spring-boot/exception-handling
 * https://github.com/thombergs/code-examples/tree/master/spring-boot/password-encoding
 * https://reflectoring.io/spring-boot-openapi/
 * https://spring.io/guides/gs/testing-web/
 */
	
This Service call another 2 services:

product.service.url=http://localhost:8091/product-api/v1
customer.service.url:http://localhost:8092/customer-api/v1

 /*
  * WebFlux Does Not Catch MethodArgumentNotValidException and Go to ServerWebInputException
  * @ExceptionHandler(MethodArgumentNotValidException.class )
  * public BeanValidationErrorResponse handleBindingException(MethodArgumentNotValidException ex) {
  *
  * It Works Fine With @ExceptionHandler(WebExchangeBindException.class)
  */
	 
@Bean //create Bean to Be Autowired
public WebClient.Builder getWebClientBuilder(){
	return WebClient.builder();
}
	
#Example to POST Customer  MicroService
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

#Example to GET Product  MicroService
@Service
public class ProductClient {

	@Autowired
	WebClient.Builder webClientBuilder;

	private final WebClient webClient;
	
	public ProductClient( @Value("${product.service.url}") String url) {
		
		this.webClient = WebClient.builder()
									.baseUrl(url)
									.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
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
