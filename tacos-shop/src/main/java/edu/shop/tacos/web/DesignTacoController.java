package edu.shop.tacos.web;

import java.util.ArrayList;
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
import edu.shop.tacos.data.TacoRepository;
import edu.shop.tacos.model.Ingredient;
import edu.shop.tacos.model.Order;
import edu.shop.tacos.model.Taco;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private TacoRepository tacoRepository;

	@GetMapping
	public String showDesignForm(Model model) {

		fillIngredients(model);
		return "design";
	}

	@PostMapping
	public String processDesign(@Valid @ModelAttribute(name = "tktn") Taco design, Errors errors, Model model,
			@ModelAttribute Order order) {
		if (errors.hasErrors()) {
			fillIngredients(model);
			return "design";
		}

		Taco saved = tacoRepository.save(design);
		order.addDesign(saved);

		log.info("Procesando....." + design);

		return "redirect:/orders/current";

	}

	@ModelAttribute(name = "tktn")
	public Taco taco() {
		return new Taco();
	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	private void fillIngredients(Model model) {

		List<Ingredient> ingredients = new ArrayList<Ingredient>();

		ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));

		for (Ingredient.Type type : Ingredient.Type.values()) {

			model.addAttribute(type.name().toLowerCase(), ingredients.stream()
					.filter(ingrendient -> ingrendient.getType().equals(type)).collect(Collectors.toList()));
		}
	}

}
