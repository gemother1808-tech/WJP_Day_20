package com.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ApiException;
import com.app.dao.UserDao;
import com.app.dto.UserResp;
import com.app.dto.UserSignupRequest;
import com.app.entities.UserEntity;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	//depcy 
	private final UserDao userDao;
	private final ModelMapper modelMapper;
	private PasswordEncoder encoder;
	

	@Override
	public UserResp registerNewUser
	(UserSignupRequest dto) {
		// 1. check for dup email
		if(userDao.existsByEmail(dto.getEmail()))
			throw new ApiException("Dup email detected !!!!!!");
		//2. req dto -> entity (de ser)
		UserEntity entity=modelMapper.map(dto, UserEntity.class);
		//encrypt password
		entity.setPassword(encoder.encode(entity.getPassword()));
		//3. save -> persistent entity -> resp dto (ser) 
		return modelMapper.map(userDao.save(entity),
				UserResp.class);
	}


	
	

}
