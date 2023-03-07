package edu.shop.tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.shop.tacos.model.Ingredient;
import edu.shop.tacos.model.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public Taco save(Taco design) {
		long tacoId = saveTacoInfo(design);
		design.setId(tacoId);

		for (Ingredient ingredient : design.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}

		return design;
	}

	private long saveTacoInfo(Taco design) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"INSERT INTO Taco (name, createdAt) VALUES (?,?)", Types.VARCHAR, Types.TIMESTAMP);	
		pscf.setReturnGeneratedKeys(true);

		design.setCreatedAt(new Date());
		jdbc.update(pscf.newPreparedStatementCreator(
				new Object[] { design.getName(), new Timestamp(design.getCreatedAt().getTime()) }), keyHolder);

		Number n = keyHolder.getKey();
		return n.longValue();
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {

		jdbc.update("INSERT INTO Taco_Ingredients (taco_id, ingredient_id) VALUES (?,?)", tacoId, ingredient.getId());
	}

}
