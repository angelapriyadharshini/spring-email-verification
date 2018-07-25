package com.shalom.onlinetest.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shalom.onlinetest.dao.IRoleDAO;
import com.shalom.onlinetest.dao.IUserDAO;
import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.error.EmailExistsException;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IRoleDAO roleDAO;

	@Override
	@Transactional
	public void registerUser(UserDTO userDto) {

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setEnabled(userDto.isEnabled());

		user.setRoles(Arrays.asList(roleDAO.findByRoleName("ROLE_CANDIDATE")));

		userDAO.save(user);
	}

	@Override
	@Transactional
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

		@Override
	public User findByEmailAndPassword(String email, String password) {
		
		return userDAO.findByEmailAndPassword(email,password);
	}

	@Transactional
	@Override
	public User loginUser(UserDTO userDTO) {
		return userDAO.loginUser(userDTO);
	}

	
}
