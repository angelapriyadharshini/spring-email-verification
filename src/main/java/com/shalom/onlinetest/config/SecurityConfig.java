	package com.shalom.onlinetest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@ComponentScan(basePackages = "com.shalom.onlinetest")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// add reference to security data source
	
	@Autowired
	private DataSource securityDS;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// use jdbc authentication
		auth.jdbcAuthentication().dataSource(securityDS).usersByUsernameQuery(
				   "select email,password, enabled from user where email=?")
		  .authoritiesByUsernameQuery(
		   "select user_id, role_id from role_user where user_id=?");
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// .anyRequest().authenticated()
				.antMatchers("/").hasRole("CANDIDATE")
				.antMatchers("/recruiters/**").hasRole("RECRUITER")
				.antMatchers("/admins/**").hasRole("ADMIN")
				.and().formLogin().loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
				.loginProcessingUrl("/authenticateLogin").permitAll().and().logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/access-denied");
	}

}
