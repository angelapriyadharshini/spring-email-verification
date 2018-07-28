package com.shalom.onlinetest.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.User;

@Service
public interface IUserService extends UserDetailsService {
	public void registerUser(UserDTO userDto);

	public User findByUsername(String username);

	public User loginUser(UserDTO userDTO);

	public User findByUsernameAndPassword(String username, String password);
}
