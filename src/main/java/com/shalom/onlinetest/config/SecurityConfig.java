package com.shalom.onlinetest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// add reference to security data source
	
	@Autowired
	private DataSource securityDS;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// use jdbc authentication
		auth.jdbcAuthentication().dataSource(securityDS).usersByUsernameQuery(
				   "select username,password, enabled from user where username=?")
		  .authoritiesByUsernameQuery(
		   "select username, role from role_user where username=?");
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

}
