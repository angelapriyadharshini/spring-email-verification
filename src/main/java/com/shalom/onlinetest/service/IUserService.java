package com.shalom.onlinetest.service;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.error.UserExistException;
import com.shalom.onlinetest.persistence.entity.User;

public interface IUserService {
	public User registerUser(UserDTO userDto) throws UserExistException;
}
