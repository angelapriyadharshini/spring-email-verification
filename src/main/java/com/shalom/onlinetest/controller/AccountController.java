package com.shalom.onlinetest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ModelAndView registerNewUser(@ModelAttribute("user") UserDTO userDto, BindingResult result,
			WebRequest request, Errors errors) {
		User registeredUser = new User();
//		try {
			if(!result.hasErrors()) {
				registeredUser = createNewUserAccount(userDto,result);
			}
//		}catch(Exception e) {
//			
//		}
		if(result.hasErrors()) {
			return new ModelAndView("registration","user",userDto);
		}
		else {
			return new ModelAndView("registrationSuccess","user",userDto);
		}
	}
	
	private User createNewUserAccount(UserDTO userDto, BindingResult result) {
		User registeredUser = null; 
		try {
			registeredUser = service.registerUser(userDto);
		}catch(EmailExistsException e) {
			e.getMessage();
		}
		return registeredUser;
	}
}







