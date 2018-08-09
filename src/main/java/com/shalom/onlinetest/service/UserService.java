package com.shalom.onlinetest.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shalom.onlinetest.dao.IRoleDAO;
import com.shalom.onlinetest.dao.IUserDAO;
import com.shalom.onlinetest.dao.TokenDAO;
import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.Role;
import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.entity.VerificationToken;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IRoleDAO roleDAO;

	@Autowired
	private TokenDAO tokenDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// logger
	private Logger logger = Logger.getLogger(getClass().getName());

	@Override
	@Transactional
	public User registerUser(UserDTO userDto) {

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUsername(userDto.getUserName());
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(hashedPassword);
		user.setEnabled(userDto.isEnabled());
		user.setEmail(userDto.getEmail());

		user.setRoles(Arrays.asList(roleDAO.findByRoleName("ROLE_CANDIDATE")));
		userDAO.save(user);
		return user;
	}

	@Override
	@Transactional
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {

		return userDAO.findByUsernameAndPassword(username, password);
	}

	@Transactional
	@Override
	public User loginUser(UserDTO userDTO) {
		return userDAO.loginUser(userDTO);
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userDAO.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		try {
			if (user.isEnabled() != true) {
				throw new UsernameNotFoundException("Please enable your account.");
			}
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, true, mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
	}

	@Override
	public void createVerificationToken(User user, String token) {
		VerificationToken newUserToken = new VerificationToken(token, user);
		tokenDAO.save(newUserToken);
	}

	@Override
	@Transactional
	public VerificationToken getVerificationToken(String verificationToken) {
		return tokenDAO.findByToken(verificationToken);
	}

}
