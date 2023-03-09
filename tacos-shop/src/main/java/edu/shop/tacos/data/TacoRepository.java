package edu.shop.tacos.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.shop.tacos.model.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
	
	

}
