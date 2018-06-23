package com.shalom.onlinetest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String showHome() {
		return "home";
	}
	
	// recruiters
	@GetMapping("/recruiters")
	public String showRecruiters() {
		return "recruiters";
	}
	
	
}
