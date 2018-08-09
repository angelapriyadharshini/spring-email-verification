package com.shalom.onlinetest.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.service.IUserService;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private MessageSource messages;
	
//	@Autowired
//	private JavaMailSender mailSender;
	
	@Autowired
	private MailSender mailSender;

	@Override
	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
		this.confirmRegistration(event);
		
	}

	private void confirmRegistration(OnRegistrationSuccessEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userService.createVerificationToken(user,token);
		
		String recipient = user.getEmail();
		String subject = "Registration Confirmation";
        String url 
          = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;
        String message = messages.getMessage("message.registrationSuccessConfimationLink", null, event.getLocale());
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(message + " rn" + "http://localhost:8080" + url);
        System.out.println(url);
        mailSender.send(email);
		
	}
	
	
}
