package com.shalom.onlinetest.dao;

import com.shalom.onlinetest.entity.Role;

public interface IRoleDAO {
	public Role findByRoleName(String role);
}
