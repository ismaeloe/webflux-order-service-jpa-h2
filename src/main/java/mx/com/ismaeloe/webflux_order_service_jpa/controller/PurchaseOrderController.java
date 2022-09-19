package mx.com.ismaeloe.webflux_order_service_jpa.controller;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import mx.com.ismaeloe.exceptions.ResourceNotFoundException;

import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderRequestDto;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderResponseDto;
import mx.com.ismaeloe.webflux_order_service_jpa.service.OrderFulfillmentService;
import mx.com.ismaeloe.webflux_order_service_jpa.service.OrderQueryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PurchaseOrderController.PATH) //"/order-api/v1"
public class PurchaseOrderController {

	public static final String PATH = "/order-api/v1"; 
	@Autowired
	private OrderFulfillmentService orderFulfillmentService;
	
	@Autowired
	private OrderQueryService orderQueryService;
	
	@PostMapping
//OKpublic Mono<PurchaseOrderResponseDto> createOrder(@Valid @RequestBody Mono<PurchaseOrderRequestDto> poRequestDto)
//OK
	public ResponseEntity<Mono<PurchaseOrderResponseDto>> createOrder(@Valid @RequestBody Mono<PurchaseOrderRequestDto> poRequestDto)
//public Mono<ResponseEntity<PurchaseOrderResponseDto>> createOrder(@Valid @RequestBody Mono<PurchaseOrderRequestDto> poRequestDto)
	{
		//ServletUriComponentsBuild
		UUID id = UUID.randomUUID();

        URI uri = UriComponentsBuilder.newInstance().pathSegment(PATH, id.toString()).build().toUri();

		//UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(PATH + "/" + id );
		
		
		Mono<PurchaseOrderResponseDto> mono = this.orderFulfillmentService.processOrder(poRequestDto);
		
		Integer i = 0;
		
		//String location = mono.map( poResponseDto ->  poResponseDto.getIdOrder()).map(i -> String.format("%s/%d", PATH,i)).block();
		
	
		//TODO switchIfEmpty
		//mono.switchIfEmpty( Mono.error( new IllegalArgumentException( "Customer or Product not found")) );
		//this.orderFulfillmentService.processOrder(poRequestDto).switchIfEmpty(null)
		//.switchIfEmpty( Mono.error( new IllegalArgumentException(id))
		//.switchIfEmpty( Mono.error(new ResourceNotFoundException(ProductController.PATH ,"id" ,id)))	//if Mono.empty() = Not Found
		
		//OK return this.orderFulfillmentService.processOrder(poRequestDto);
		//OK 
		return ResponseEntity.created(uri).body(mono); //return ResponseEntity.status(HttpStatus.CREATED).header("Location", location).body(mono);
		/*X
		return mono.flatMap( po -> {
			//UriComponentsBuilder.newInstance().pathSegment(PATH, po.getIdOrder().toString().toUri())
			return  ResponseEntity.created(uri).build().body(mono);
		});*/
	}

	/* OK IllegalArgumentException
	 * Using @ExceptionHandler mx.com.ismaeloe.webflux_order_service_jpa.controller.GlobalExceptionHandlerController#illegalArgumentException
	 * {
		    "message": "throw new IllegalArgumentException",
		    "timestamp": "2022-09-12T14:15:43.597553-05:00",
		    "trace_id": "0ccf2265-b94d-45f7-a65e-f97bcdab61c1",
		    "status": 400
		}
	 */
	@GetMapping("/illegal")
	public String getIllegal() {
		boolean b = true;
		
		if (b) {
			throw new IllegalArgumentException("throw new IllegalArgumentException");
		}
		return "illegal ok";
	}
	
	/* OK NullPointerException
	 * Using @ExceptionHandler mx.com.ismaeloe.webflux_order_service_jpa.controller.GlobalExceptionHandlerController#exception
	 * {
		    "message": "Internal Server Error =null",
		    "timestamp": "2022-09-12T13:56:48.1464125-05:00",
		    "trace_id": "1b91a930-64b7-4815-b97f-15ce2e84de24",
		    "status": 500
		}
	 */
	@GetMapping("/null")
	public String getNull() {
		
		Optional.of(null); //return NullPointerException
	
		return "NullPointerException ok";
	}
	
	/* 
	 * OK NoSuchElementException
	 *  HTTP GET "/order-api/v1/NoSuchElementException"
	 *  Using @ExceptionHandler ..GlobalExceptionHandlerController#handleIllegalArgumentNoSuchElementException(RuntimeException)
	 *  {
		    "message": "No value present",
		    "timestamp": "2022-09-12T14:19:41.7408848-05:00",
		    "trace_id": "8e2b5b88-b0b2-4d10-95e8-4d6d131cce88",
		    "status": 400
		} 
	 */
	@GetMapping("/NoSuchElementException")
	public String getNoSuchElementException() {
		
		// if (value == null) { throw new NoSuchElementException("No value present");}
		 Optional.empty().get();	
		
		return "NoSuchElementException ok";
	}
	
	@GetMapping(value = "/customer/{idCustomer}")
	public Flux<PurchaseOrderResponseDto> getAllByIdCustomer(@PathVariable Integer idCustomer)
	{
		return this.orderQueryService.getProductByIdCustomer(idCustomer);
	}

}
