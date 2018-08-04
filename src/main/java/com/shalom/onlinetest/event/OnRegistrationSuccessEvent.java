package com.shalom.onlinetest.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.shalom.onlinetest.entity.User;

public class OnRegistrationSuccessEvent extends ApplicationEvent {

	private String appUrl;
	private Locale locale;
	private User user;

	public OnRegistrationSuccessEvent(User user, Locale locale, String appUrl) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
