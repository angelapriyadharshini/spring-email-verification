package com.shalom.onlinetest.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Logger;

import org.hibernate.CacheMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.entity.VerificationToken;
import com.shalom.onlinetest.event.OnRegistrationSuccessEvent;
import com.shalom.onlinetest.service.IUserService;

//@RequestMapping("/account")
@Controller
public class AccountController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private MessageSource messages;
	
	private Logger logger = Logger.getLogger(getClass().getName());

	@GetMapping("/login")
	public String showLoginForm() {
		return "fancy-login";
	}
	
//	@PostMapping("/login")
//	public String login(@ModelAttribute("user") UserDTO userDto, BindingResult result, Model model){
//		String username = userDto.getUserName();
//		String password = userDto.getPassword();
//		if(result.hasErrors()) {
//			return "fancy-login";
//		}
//		User loggedInUser = service.findByUsernameAndPassword(username,password);
//		if(loggedInUser==null) {
//			return "fancy-login";
//		}
//		service.loginUser(userDto);
//		return "login-success";
//	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);
		return "registration";
	}

	@PostMapping("/registration")
	public String registerNewUser(@ModelAttribute("user") UserDTO userDto, BindingResult result, WebRequest request, Model model) {
		User registeredUser = new User();
		String userName = userDto.getUserName();
		if (result.hasErrors()) {
			return "registration";
		}
		registeredUser = service.findByUsername(userName);
		if(registeredUser!=null) {
			model.addAttribute("error","There is already an account with this username: " + userName);
			logger.info("There is already an account with this username: " + userName);
			return "registration";
		}

		registeredUser = service.registerUser(userDto);
		try {
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(),appUrl));
		}catch(Exception re) {
			re.printStackTrace();
//			throw new Exception("Error while sending confirmation email");
		}
		return "registrationSuccess";
	}
	
	@GetMapping("/confirmRegistration")
	public String confirmRegistration(WebRequest request, Model model,@RequestParam("token") String token) {
		Locale locale=request.getLocale();
		VerificationToken verificationToken = service.getVerificationToken(token);
		if(verificationToken == null) {
			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return "redirect:access-denied";
		}
		User user = verificationToken.getUser();
		Calendar calendar = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
			String message = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", message);
			return "redirect:access-denied";
		}
		
		user.setEnabled(true);
		service.enableRegisteredUser(user);
		return null;
	}
}
