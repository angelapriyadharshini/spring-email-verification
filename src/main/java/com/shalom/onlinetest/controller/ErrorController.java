package com.shalom.onlinetest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	@GetMapping("/access-denied")
	public String showCustomError() {
		return "access-denied";
	}
}
