package edu.shop.tacos.data;

import org.springframework.data.repository.CrudRepository;

import edu.shop.tacos.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
	
	

}
