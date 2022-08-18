package mx.com.ismaeloe.webflux_order_service_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderRequestDto;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderResponseDto;
import mx.com.ismaeloe.webflux_order_service_jpa.service.OrderFulfillmentService;
import mx.com.ismaeloe.webflux_order_service_jpa.service.OrderQueryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order-api/v1")
public class PurchaseOrderController {

	@Autowired
	private OrderFulfillmentService orderFulfillmentService;
	
	@Autowired
	private OrderQueryService orderQueryService;
	
	@PostMapping
	public Mono<PurchaseOrderResponseDto> createOrder(@RequestBody Mono<PurchaseOrderRequestDto> poRequestDto)
	{
		return this.orderFulfillmentService.processOrder(poRequestDto);
	}
	
	
	@GetMapping(value = "/customer/{idCustomer}")
	public Flux<PurchaseOrderResponseDto> getAllByIdCustomer(@PathVariable Integer idCustomer)
	{
		return this.orderQueryService.getProductByIdCustomer(idCustomer);
	}

}
