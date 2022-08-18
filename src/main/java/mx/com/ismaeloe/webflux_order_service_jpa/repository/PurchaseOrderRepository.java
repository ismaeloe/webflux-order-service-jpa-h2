package mx.com.ismaeloe.webflux_order_service_jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.ismaeloe.webflux_order_service_jpa.entity.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

	//Get Customer's Orders
	List<PurchaseOrder> findByIdCustomer(Integer idCustomer);

}
