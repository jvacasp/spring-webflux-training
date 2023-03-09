package edu.shop.tacos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Taco not exists")
public class TacoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3072801502368162688L;

}
