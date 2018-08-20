package com.shalom.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shalom.entity.Role;

@Repository
public class RoleDAO implements IRoleDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Role findByRoleName(String roleName) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Role> query = session.createQuery("from Role where role=:userRole", Role.class);
		query.setParameter("userRole", roleName);
		
		Role role = null;
		try {
			role =  query.getSingleResult();
		}catch (Exception e) {
			role = null;
		}
		return role;
	}

}
