package com.shalom.onlinetest.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shalom.onlinetest.dao.IRoleDAO;
import com.shalom.onlinetest.dao.IUserDAO;
import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.Role;
import com.shalom.onlinetest.entity.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IRoleDAO roleDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// logger
	private Logger logger = Logger.getLogger(getClass().getName());

	@Override
	@Transactional
	public void registerUser(UserDTO userDto) {

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
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
	}

}
