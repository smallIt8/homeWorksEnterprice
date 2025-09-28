package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

	@GetMapping("/")
	public String index() {
		return "index";  // подставится prefix + name + suffix = /WEB-INF/jsp/index.jsp
	}

}
