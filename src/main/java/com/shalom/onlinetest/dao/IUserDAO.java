package com.shalom.onlinetest.dao;

import com.shalom.onlinetest.entity.User;

public interface IUserDAO {
	public User findByEmail(String email);

	public User save(User user);
}
