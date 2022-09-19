package mx.com.ismaeloe.webflux_order_service_jpa.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PurchaseOrderRequestDto {

	@NotNull(message = "1001,Must Introduce a Customer")
	@Min(value = 1 ,message = "1001,Customer must be greater than Cero")
	private Integer idCustomer;

	@NotEmpty(message = "1002,Must Introduce a Product") //"1002,PRODUCT,Must Introduce a Product"
	private String  idProduct;
}
