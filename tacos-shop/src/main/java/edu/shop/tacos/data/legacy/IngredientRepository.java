package edu.shop.tacos.data.legacy;

import edu.shop.tacos.model.Ingredient;

public interface IngredientRepository {

		Iterable<Ingredient> findAll();
		Ingredient findOne (String id);
		void save(Ingredient ingredient);
	
}
