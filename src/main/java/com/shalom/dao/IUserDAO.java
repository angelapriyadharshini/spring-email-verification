package com.shalom.dao;

import com.shalom.dto.UserDTO;
import com.shalom.entity.User;

public interface IUserDAO {
	public User findByUsername(String username);

	public void save(User user);
	
	public User loginUser(UserDTO userDTO);

	public User findByUsernameAndPassword(String username, String password);
}
