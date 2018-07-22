package com.shalom.onlinetest.service;

import org.springframework.stereotype.Service;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.error.EmailExistsException;

@Service
public interface IUserService {
	public void registerUser(UserDTO userDto) throws EmailExistsException;

	public User findByEmail(String email);
}
