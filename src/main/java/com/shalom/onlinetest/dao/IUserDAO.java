package com.shalom.onlinetest.dao;

import com.shalom.onlinetest.entity.User;

public interface IUserDAO {
	public User findByEmail(String email);

	public void save(User user);
}
