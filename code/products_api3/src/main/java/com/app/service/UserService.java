package com.app.service;

import com.app.dto.UserResp;
import com.app.dto.UserSignInRequest;
import com.app.dto.UserSignupRequest;

public interface UserService {

	UserResp registerNewUser(UserSignupRequest dto);

	

}
