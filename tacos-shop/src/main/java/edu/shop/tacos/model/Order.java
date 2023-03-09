package edu.shop.tacos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date placedAt;

	@ManyToMany(targetEntity = Taco.class)
	private List<Taco> tacos;

	@NotBlank(message = "The name is required")
	private String deliveryName;

	@NotBlank(message = "The street is required")
	private String deliveryStreet;

	@NotBlank(message = "The city is required")
	private String deliveryCity;

	@NotBlank(message = "The state is required")
	private String deliveryState;

	@NotBlank(message = "The zip is required")
	private String deliveryZip;

	@CreditCardNumber(message = "Not a valid ccNumber")
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 0, message = "Not a valid ccCVV")
	private String ccCVV;

	public void addDesign(Taco design) {
		if (tacos == null)
			tacos = new ArrayList<>();
		tacos.add(design);
	}

	@PrePersist
	void setCreatedAt() {
		this.placedAt = new Date();
	}

}
