package edu.shop.tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.shop.tacos.data.IngredientRepository;
import edu.shop.tacos.model.Ingredient;
import edu.shop.tacos.model.Taco;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@GetMapping
	public String showDesignForm(Model model) {
			
		fillIngredients(model);		
		model.addAttribute("tktn", new Taco());
		
		return "design";
	}
	
	@PostMapping
	public String processDesign (@Valid @ModelAttribute(name="tktn") Taco design, Errors errors, Model model) {
		if (errors.hasErrors()) {
			fillIngredients(model);
			return "design";
		}
		
		log.info("Procesando....."+design);
		return "redirect:/orders/current";
		
	}
	
	private void fillIngredients(Model model) {
	
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));
		
		for (Ingredient.Type type: Ingredient.Type.values()) {
		
			model.addAttribute(
					type.name().toLowerCase(),
					ingredients.stream().filter(ingrendient -> ingrendient.getType().equals(type)).
					collect(Collectors.toList()));
		}
	}
	
}
