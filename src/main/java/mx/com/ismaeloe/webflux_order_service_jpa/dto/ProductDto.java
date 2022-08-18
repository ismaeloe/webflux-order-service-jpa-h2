package mx.com.ismaeloe.webflux_order_service_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
	
	private String id;
	private String description;
	private Double price;
	
}
