package com.shalom.onlinetest.dao;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shalom.onlinetest.entity.User;
import com.shalom.onlinetest.entity.VerificationToken;

@Repository
public class TokenDAO implements ITokenDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public VerificationToken findByToken(String token) {
		Session session = sessionFactory.getCurrentSession();
		Query<VerificationToken> query = session.createQuery("from VerificationToken where token=:token",
				VerificationToken.class);
		session.setCacheMode(CacheMode.IGNORE);
		query.setParameter("token", token);

		VerificationToken userToken = null;
		try {
			userToken = query.getSingleResult();
		} catch (Exception e) {
			userToken = null;
			e.printStackTrace();
		}
		return userToken;
	}

	@Override
	public VerificationToken findByUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query<VerificationToken> query = session.createQuery("from VerificationToken where user=:user",
				VerificationToken.class);
		session.setCacheMode(CacheMode.IGNORE);
		query.setParameter("user", user);

		VerificationToken userToken = null;
		try {
			userToken = query.getSingleResult();
		} catch (Exception e) {
			userToken = null;
			e.printStackTrace();
		}
		return userToken;
	}

	@Override
	public void save(VerificationToken newUserToken) {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		session.setCacheMode(CacheMode.IGNORE);
		session.saveOrUpdate(newUserToken);
	}

}
