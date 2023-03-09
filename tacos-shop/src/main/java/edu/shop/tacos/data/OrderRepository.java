package edu.shop.tacos.data;

import org.springframework.data.repository.CrudRepository;

import edu.shop.tacos.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
	
	

}
