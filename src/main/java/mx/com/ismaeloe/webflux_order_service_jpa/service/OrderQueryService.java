package mx.com.ismaeloe.webflux_order_service_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderResponseDto;

import mx.com.ismaeloe.webflux_order_service_jpa.repository.PurchaseOrderRepository;
import mx.com.ismaeloe.webflux_order_service_jpa.util.EntityDtoUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

	@Autowired
	private PurchaseOrderRepository poRepository;
	
	public Flux<PurchaseOrderResponseDto> getProductByIdCustomer(Integer idCustomer)
	{
		/* JPA BLOCKING
		 * List<PurchaseOrder> listPOrders = this.poRepository.findByIdCustomer(idCustomer);
		 * Flux.fromIterable(listPOrders);
		 */
		 
		//GET Stream<PurchaseOrder>
		return Flux.fromStream( () -> this.poRepository.findByIdCustomer(idCustomer)
														.stream())	//JPA BLOCKING
					//.map( purchaseOrder -> EntityDtoUtil.getPurchaseOrderResponseDto(purchaseOrder));
					  .map( EntityDtoUtil::getPurchaseOrderResponseDto )
					  .subscribeOn( Schedulers.boundedElastic() );
	}
}
