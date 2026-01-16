package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.UserEntity;

public interface UserDao extends JpaRepository<UserEntity,Long> {
//to fetch user details by email
	Optional<UserEntity> findByEmail(String email);
	//add dup check for email
		boolean existsByEmail(String email);
		//add derived query method
		Optional<UserEntity> findByEmailAndPassword(String em,String pass);

}
