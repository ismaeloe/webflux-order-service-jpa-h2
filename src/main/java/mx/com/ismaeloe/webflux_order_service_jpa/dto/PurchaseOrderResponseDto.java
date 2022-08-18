package mx.com.ismaeloe.webflux_order_service_jpa.dto;

import lombok.Data;

@Data
public class PurchaseOrderResponseDto {

	private Integer idOrder;
	
	private Integer idCustomer;
	private String  idProduct;
	
	private Double amount;

	private OrderStatus status;
}
