package com.shalom.dao;

import com.shalom.entity.User;
import com.shalom.entity.VerificationToken;

public interface ITokenDAO {
	public VerificationToken findByToken(String token);

	public VerificationToken findByUser(User user);

	public void save(VerificationToken token);

}
