package com.shalom.onlinetest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shalom.onlinetest.persistence.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
