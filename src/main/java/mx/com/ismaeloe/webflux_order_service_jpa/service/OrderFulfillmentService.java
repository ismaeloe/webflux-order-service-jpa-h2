package mx.com.ismaeloe.webflux_order_service_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.ismaeloe.webflux_order_service_jpa.client.CustomerClient;
import mx.com.ismaeloe.webflux_order_service_jpa.client.ProductClient;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.EstRequestContext;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderRequestDto;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderResponseDto;
import mx.com.ismaeloe.webflux_order_service_jpa.repository.PurchaseOrderRepository;
import mx.com.ismaeloe.webflux_order_service_jpa.util.EntityDtoUtil;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderFulfillmentService {

	@Autowired
	private ProductClient productClient;

	@Autowired
	private CustomerClient customerClient;
	
	@Autowired
	private PurchaseOrderRepository poRepository;
	
	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> poRequestDtoMono) {

		//EstRequestContext
		//Get ProductDto
		//Get PurchaseOrder
		/*OK WithOut METHOD REFERENCE
		 poRequestDtoMono.map( poRequestDtoMono -> new EstRequestContext(poRequestDtoMono) )				
						.flatMap( estRequestContext -> this.productRequestResponse(estRequestContext))	
						.doOnNext(estRequestContext -> EntityDtoUtil.setTransactionRequestDto(estRequestContext))	
						.flatMap( estRequestContext -> this.customerRequestResponse(estRequestContext))
						.map(	  estRequestContext -> EntityDtoUtil.getPurchaseOrder(estRequestContext))
						//.publishOn(null) ALTERNATIVE to subscribeOn( Schedulers.boundedElastic())
						.map( po -> this.poRepository.save(po))	// BLOCKING and AFFECTING PERFORMANCE
						.map( po -> EntityDtoUtil.getPurchaseOrderResponseDto(po))
						.subscribeOn( Schedulers.boundedElastic());
			*/			

		/*
		 *OK With METHOD REFERENCE / WithOut 
		 * 
		 * Because this.poRepository::save )  is  BLOCKING and AFFECTING PERFORMANCE
		 *  we add .subscribeOn( Schedulers.boundedElastic())
		 */
		return poRequestDtoMono.map( EstRequestContext::new) //.flatMap(this::productRequestResponse).
				 		.flatMap( this::productRequestResponse )//TODO .switchIfEmpty(null)
				 		.doOnNext(EntityDtoUtil::setTransactionRequestDto)
				 		.flatMap( this::customerRequestResponse )
						.map( EntityDtoUtil::getPurchaseOrder )
						//.publishOn(null) ALTERNATIVE to subscribeOn( Schedulers.boundedElastic())
						.map( this.poRepository::save )	// BLOCKING and AFFECTING PERFORMANCE
						.map( EntityDtoUtil::getPurchaseOrderResponseDto)
						.subscribeOn( Schedulers.boundedElastic());
	}
	
	private Mono<EstRequestContext> productRequestResponse(EstRequestContext erc) {
		/*
		 * Option 1:
		 * return Mono.just( erc);
		 * return Mono.just( new EstRequestContext(); )
		 * 
		 * Option 2: Lamda productDto -> erc.setProductDto(productDto)
		 * return this.productClient.getProductById( erc.getPurchaseOrderRequestDto().getIdProduct() )
		 * 						.doOnNext( productDto -> erc.setProductDto(productDto))
		 * 						.thenReturn(erc);
		 * 
		 * Option 3: Method Reference erc::setProductDto
		 */
		 return this.productClient.getProductById( erc.getPurchaseOrderRequestDto().getIdProduct()) //1. Get Mono<ProductDto>
				 				.doOnNext( erc::setProductDto)	//2. Set ProductDto
				 				.thenReturn(erc); 				//3. return Mono<erc>
	}

	private Mono<EstRequestContext> customerRequestResponse(EstRequestContext erc)
	{
		/*
		 * Option 1:
		 * return Mono.just( erc);
		 * return Mono.jst( new EstRequestContext(); )
		 * 
		 * Option 2:
		 * return this.customerClient.authorizeTransaction( erc.getTransactionRequestDto() ) 	// 1. return Mono<TransactionResponseDto>
		 * 					.map( txResponseDto -> {											// 2. Set txResponse To erc 
											erc.setTransactionResponseDto(txResponseDto);
									return 	erc;
							})
		 * 					.doOnNext( txResponseDto -> erc.setTransactionResponseDto(txResponseDto) ) // 2. transform txResponse To erc
		 *					.thenReturn( erc );
		 */
		return this.customerClient.authorizeTransaction( erc.getTransactionRequestDto() ) 	// 1. return Mono<TransactionResponseDto>
							.doOnNext( erc::setTransactionResponseDto )
							.thenReturn( erc );
	}
}
