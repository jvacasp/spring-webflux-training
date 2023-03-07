package edu.shop.tacos.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {

	private long id;
	private Date createdAt;

	private List<Taco> tacos;
	
	private Order placedAt;
	
	@NotBlank(message = "The name is required")
	private String name;

	@NotBlank(message = "The street is required")
	private String street;

	@NotBlank(message = "The city is required")
	private String city;

	@NotBlank(message = "The state is required")
	private String state;

	@NotBlank(message = "The zip is required")
	private String zip;

	@CreditCardNumber(message = "Not a valid ccNumber")
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 0, message = "Not a valid ccCVV")
	private String ccCVV;

	public void addDesign(Taco design) {
		tacos.add(design);
	}

}