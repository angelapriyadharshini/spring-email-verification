package com.shalom.onlinetest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "fancy-login";
	}

	// add request mapping for access denied

	@GetMapping("/access-denied")
	public String showCustomError() {
		return "access-denied";
	}
}
