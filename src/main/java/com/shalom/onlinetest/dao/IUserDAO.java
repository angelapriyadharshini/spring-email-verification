package com.shalom.onlinetest.dao;

import com.shalom.onlinetest.dto.UserDTO;
import com.shalom.onlinetest.entity.User;

public interface IUserDAO {
	public User findByEmail(String email);

	public void save(User user);
	
	public User loginUser(UserDTO userDTO);

	public User findByEmailAndPassword(String email, String password);
}
