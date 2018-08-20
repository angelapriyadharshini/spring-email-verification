package com.shalom.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shalom.service.IUserService;

@Configuration
//@ComponentScan(basePackages = "com.shalom")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// add reference to security data source
	@Autowired
	private IUserService userService;
	
	@Autowired
	private DataSource securityDS;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		// use jdbc authentication
		auth.jdbcAuthentication().dataSource(securityDS);
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// .anyRequest().authenticated()
				.antMatchers("/").hasRole("CANDIDATE")
				.antMatchers("/recruiters/**").hasRole("RECRUITER")
				.antMatchers("/admins/**").hasRole("ADMIN")
				.and().formLogin().loginPage("/login")
				.loginProcessingUrl("/authenticateLogin").permitAll().and().logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/access-denied");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		auth.setHideUserNotFoundExceptions(false);
		return auth;
	}

}
