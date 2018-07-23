package com.shalom.onlinetest.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.service.IUserService;

//@RequestMapping("/account")
@Controller
public class AccountController {

	@Autowired
	private IUserService service;
	
	private Logger logger = Logger.getLogger(getClass().getName());

	@GetMapping("/login")
	public String login() {
		return "fancy-login";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);
		return "registration";
	}

	@PostMapping("/registration")
	public String registerNewUser(@ModelAttribute("user") UserDTO userDto, BindingResult result, Model model) {
		User registeredUser = new User();
		String email = userDto.getEmail();
		if (result.hasErrors()) {
			return "registration";
		}
		registeredUser = service.findByEmail(email);
		if(registeredUser!=null) {
			model.addAttribute("error","There is already an account with this email: " + email);
			logger.info("There is already an account with this email: " + email);
			return "registration";
		}
		service.registerUser(userDto);
		return "registrationSuccess";
	}
}
