package edu.shop.tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.shop.tacos.data.IngredientRepository;
import edu.shop.tacos.model.Ingredient;

@Component
public class IngredientConverter implements Converter<String, Ingredient> {
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Override
	public Ingredient convert(String id) {
		return ingredientRepository.findOne(id);
	}

}
