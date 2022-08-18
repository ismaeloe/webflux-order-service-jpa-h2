package mx.com.ismaeloe.webflux_order_service_jpa.dto;

import lombok.Data;

@Data
public class PurchaseOrderRequestDto {

	private Integer idCustomer;
	private String  idProduct;
}
