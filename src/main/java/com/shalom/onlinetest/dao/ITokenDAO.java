package com.shalom.onlinetest.dao;

import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.entity.VerificationToken;

public interface ITokenDAO {
	public VerificationToken findByToken(String token);

	public VerificationToken findByUser(User user);

	public void save(VerificationToken token);

}
