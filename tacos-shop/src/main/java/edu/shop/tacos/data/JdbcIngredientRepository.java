package edu.shop.tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.shop.tacos.model.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("SELECT id,name, type FROM Ingredient", this::mapRowToIngredient);		
	}

	@Override
	public Ingredient findOne(String id) {
		return jdbc.queryForObject("SELECT id,name, type FROM Ingredient WHERE id=?", this::mapRowToIngredient,id);	
	}

	@Override
	public void save(Ingredient ingredient) {
		jdbc.update("INSERT INTO Ingredient (id, name, type) VALUES (?,?,?)", ingredient.getId(),ingredient.getName(),ingredient.getType());
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
		return new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
	}
	

}
