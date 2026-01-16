package com.cdac.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.User;

public interface UserDao extends JpaRepository<User, Long> {
//add finder method to validate dup email
	boolean existsByEmail(String email);

	// sign in
	Optional<User> findByEmailAndPassword(String em, String pass);
	//get list of users born after date
	List<User> findByDobAfter(LocalDate date);
}
