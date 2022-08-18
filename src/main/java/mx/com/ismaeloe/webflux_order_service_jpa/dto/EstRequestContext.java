package mx.com.ismaeloe.webflux_order_service_jpa.dto;

import lombok.Data;

@Data
public class EstRequestContext {
	
	private ProductDto productDto;
	
	private PurchaseOrderRequestDto purchaseOrderRequestDto;
	
	private TransactionRequestDto transactionRequestDto;
	
	private TransactionResponseDto transactionResponseDto; 

	public EstRequestContext(PurchaseOrderRequestDto _purchaseOrderRequestDto) {
		this.purchaseOrderRequestDto = _purchaseOrderRequestDto;
	}
}
