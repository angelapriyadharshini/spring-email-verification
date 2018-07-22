package com.shalom.onlinetest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.error.EmailExistsException;
import com.shalom.onlinetest.service.IUserService;

//@RequestMapping("/account")
@Controller
public class AccountController {

	@Autowired
	private IUserService service;

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
		if(registeredUser==null) {
			model.addAttribute("registrationFailed","User already exists.");
			return "registration";
		}
		
		return "registrationSuccess";
	}
}
