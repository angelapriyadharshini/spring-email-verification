package com.shalom.onlinetest.service;

import javax.transaction.Transactional;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.error.UserExistException;
import com.shalom.onlinetest.persistence.dao.UserRepository;
import com.shalom.onlinetest.persistence.entity.User;

public class UserService implements IUserService {

	private UserRepository rep;

	@Transactional
	@Override
	public User registerUser(UserDTO userDto) throws UserExistException {

		if (emailAlreadyExists(userDto.getEmail())) {
			throw new UserExistException("There is already an account with this email: " + userDto.getEmail());
		}
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setEnabled(userDto.isEnabled());
		
		return rep.save(user);
	}

	private boolean emailAlreadyExists(String email) {
		User user = rep.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

}
