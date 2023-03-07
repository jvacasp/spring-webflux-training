package edu.shop.tacos.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MyErrorController implements ErrorController {

	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public String getErrorPath() {
		return null;
	}

	@RequestMapping("/error")
	public String handlerError(Model model, WebRequest request) {

		Map<String, Object> errorMap = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));		
		model.addAttribute("msg", errorMap.get("trace"));
		
		log.error("An error has happened: "+errorMap.get("trace"));
		return "error";

	}

}
