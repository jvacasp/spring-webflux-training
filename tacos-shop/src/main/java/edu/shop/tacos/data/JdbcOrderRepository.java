package edu.shop.tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.shop.tacos.model.Order;
import edu.shop.tacos.model.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInsert;
	private SimpleJdbcInsert orderTacoInsert;
	private ObjectMapper objectMapper;

	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInsert = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
		this.orderTacoInsert = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos")
				.usingGeneratedKeyColumns("id");
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(Order order) {
		order.setCreatedAt(new Date());
		long orderId = saveOrderInfo(order);
		order.setId(orderId);

		for (Taco taco : order.getTacos()) {
			saveTacoToOrder(taco, orderId);
		}

		return order;
	}

	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco);

		orderTacoInsert.executeAndReturnKey(values);
	}

	private long saveOrderInfo(Order order) {
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());

		long id = orderInsert.executeAndReturnKey(values).longValue();

		return id;
	}

}
