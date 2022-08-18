package mx.com.ismaeloe.webflux_order_service_jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;
import mx.com.ismaeloe.webflux_order_service_jpa.dto.OrderStatus;

@Data
@Entity
@ToString
public class PurchaseOrder {

	@Id
	@GeneratedValue
	private Integer idOrder;
	
	private Integer idCustomer;
	
	private String idProduct;
	private Double amount;
	
	private OrderStatus status;
}
