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
	public void registerUser(UserDTO userDto) throws EmailExistsException {

		if (emailAlreadyExists(userDto.getEmail())) {
			throw new EmailExistsException("There is already an account with this email: " + userDto.getEmail());
		}
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setEnabled(userDto.isEnabled());

		user.setRoles(Arrays.asList(roleDAO.findByRoleName("ROLE_CANDIDATE")));

		userDAO.save(user);
	}

	private boolean emailAlreadyExists(String email) {
		return userDAO.findByEmail(email) != null;
		// User user = rep.findByEmail(email);
		// if (user != null) {
		// return true;
		// }
		// return false;
	}

	@Override
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

}
