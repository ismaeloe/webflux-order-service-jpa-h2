package mx.com.ismaeloe.webflux_order_service_jpa.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerDto {

	private Integer idCustomer;
	private String  name;
	private Double balance;
}
