package edu.shop.tacos.web.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.shop.tacos.data.IngredientRepository;
import edu.shop.tacos.data.TacoRepository;
import edu.shop.tacos.exceptions.TacoNotFoundException;
import edu.shop.tacos.model.Taco;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "*")
public class TacoApiRestController {

	@Autowired
	private IngredientRepository ingredientRepo;

	@Autowired
	private TacoRepository tacoRepo;

	@GetMapping("/taco/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> taco = tacoRepo.findById(id);
		if (taco.isPresent())
			return new ResponseEntity<Taco>(taco.get(), HttpStatus.OK);

		else
			return new ResponseEntity<Taco>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/add", consumes = "application/json")
	public ResponseEntity<Taco> postTaco(@RequestBody Taco taco) {
		Taco tacoSaved = tacoRepo.save(taco);

		if (tacoSaved != null) {
			return new ResponseEntity<Taco>(tacoSaved, HttpStatus.CREATED);
		} else
			return new ResponseEntity<Taco>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@DeleteMapping("/taco/{id}")
	public Map<String, Boolean> deleteTaco(@PathVariable("id") Long id) throws TacoNotFoundException {

		tacoRepo.findById(id).orElseThrow(() -> new TacoNotFoundException());
		tacoRepo.deleteById(id);

		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("Taco" + id + "deleted:", true);

		return response;
	}

	@PutMapping("taco")
	public ResponseEntity<Taco> modifyTaco(@RequestBody Taco taco) throws TacoNotFoundException {
		Long id=taco.getId();
		tacoRepo.findById(id).orElseThrow(() -> new TacoNotFoundException());
		Taco t=tacoRepo.save(taco);
		return new ResponseEntity<Taco>(t,HttpStatus.OK);
	}
	
	@GetMapping("/taco/recents/{page}")
	public Iterable<Taco> getAllTacosByPage(@PathVariable("page") Integer page){
		PageRequest newPage=PageRequest.of(page, 10,Sort.by("createdAt").descending());
		
		return tacoRepo.findAll(newPage);
	}
}
