package mx.com.ismaeloe.webflux_order_service_jpa.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDto {
	
	private Integer idCustomer;
	private Double 	amount;
	private TransactionStatusEnum status;

}
