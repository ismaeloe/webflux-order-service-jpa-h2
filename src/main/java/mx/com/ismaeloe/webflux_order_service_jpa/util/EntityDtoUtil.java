package mx.com.ismaeloe.webflux_order_service_jpa.util;

import org.springframework.beans.BeanUtils;

import mx.com.ismaeloe.webflux_order_service_jpa.dto.EstRequestContext;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.OrderStatus;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.PurchaseOrderResponseDto;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.TransactionRequestDto;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.TransactionStatusEnum;
import mx.com.ismaeloe.webflux_order_service_jpa.entity.PurchaseOrder;

public class EntityDtoUtil {

	/*
	 * 1. Receive 	EstRequestContext
	 * 2. Create 	TransactionRequestDto
	 * 3. Set 		TransactionRequestDto
	 */
	public static void setTransactionRequestDto( EstRequestContext erc) {
		
		//2. New TransactionRequestDto
		TransactionRequestDto dto = new TransactionRequestDto();
		
		dto.setIdCustomer( erc.getPurchaseOrderRequestDto().getIdCustomer() );
		dto.setAmount( erc.getProductDto().getPrice() );
		
		erc.setTransactionRequestDto(dto);
	}
	
	/*
	 * 1. Receive 	EstRequestContext
	 * 2. Create 	PurchaseOrder
	 * 3. Set 		PurchaseOrderRequestDto to PurchaseOrder
	 * 4. Get		TransactionStatusEnum
	 * 5. Get		OrderStatus
	 * 6. Set		OrderStatus to PurchaseOrder
	 */
	public static PurchaseOrder getPurchaseOrder(EstRequestContext erc) {
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		purchaseOrder.setIdCustomer(erc.getPurchaseOrderRequestDto().getIdCustomer());
		purchaseOrder.setIdProduct(	erc.getPurchaseOrderRequestDto().getIdProduct() );
		purchaseOrder.setAmount(	erc.getProductDto().getPrice()   );
		
		//4
		TransactionStatusEnum txStatusEnum = erc.getTransactionResponseDto().getStatus();
		
		//5
		OrderStatus orderStatus = (TransactionStatusEnum.APPROVED.equals(txStatusEnum) ? OrderStatus.COMPLETED : OrderStatus.FAILED );
		
		//6
		purchaseOrder.setStatus(orderStatus);
		
		return purchaseOrder;
	}
	
	public static PurchaseOrderResponseDto getPurchaseOrderResponseDto( PurchaseOrder po) {
		
		PurchaseOrderResponseDto poResponseDto = new PurchaseOrderResponseDto();
		BeanUtils.copyProperties(po, poResponseDto);
		
		//In case Properties Don't Match
		//poResponseDto.setIdOrder( po.getIdOrder() );
		
		return poResponseDto;
	}
}
