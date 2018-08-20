package com.shalom.dao;

import com.shalom.entity.Role;

public interface IRoleDAO {
	public Role findByRoleName(String role);
}
