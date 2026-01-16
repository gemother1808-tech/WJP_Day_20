package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AuthResponse;
import com.app.dto.UserSignInRequest;
import com.app.dto.UserSignupRequest;
import com.app.security.JwtUtils;
import com.app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController // spring bean - containing P.L (req n rs handling logic)
//singleton , eager
@RequestMapping("/users")
@AllArgsConstructor
public class UserSignInSignUpController {
	
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private JwtUtils jwtUtils;

	

	/*
	 * 
	 * Desc - user signup URL - http://host:port/users Method - POST Payload - user
	 * registration request dto Resp - success - SC201 n sign up resp Error - error
	 * code , ApiResp - err mesg
	 * 
	 */
	@PostMapping("/signup")
	@Operation(description = "User sign up")
	public ResponseEntity<?> userSignUp(@RequestBody @Valid UserSignupRequest dto) {
		System.out.println("in user sign up " + dto);
		// invoke service method
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerNewUser(dto));

	}

	/*
	 * - URL - http://host:port/users/signin Method - POST Payload - req DTO - email
	 * , password Resp success - SC 200 + succes mesg + JWT error - SC 401 + error
	 * mesg - handled by spring security
	 * 
	 */
	@PostMapping("/signin")
	@Operation(description = "User sign in via Spring Security")
	public ResponseEntity<?> userSignIn(@RequestBody @Valid UserSignInRequest dto) {
		System.out.println("in user sign in " + dto);
		// 1. create Authentication token (UsernamePasswordAuthToken - username(em) ,
		// pwd
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),
				dto.getPassword());
		System.out.println("is authenticated " + authToken.isAuthenticated());// f
		// 2. invoke AuthenticationManager's - authenticate method - spring sec supplied
		Authentication successfulAuth = authenticationManager.authenticate(authToken);
		// in case of failure - throws AuthenticationException
		// in case of success- rets user details object - within auth
		// 3. => success
		System.out.println("is authenticated " + successfulAuth.isAuthenticated());// true
		System.out.println("principal " + successfulAuth.getPrincipal());// user details + granted authorities
		System.out.println("principal class" + successfulAuth.getPrincipal().getClass());// com.app.entities.UserEntity
																							// - UserDetails
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new AuthResponse("successful login ....", 
						jwtUtils.generateJwtToken(successfulAuth)
						));
	}

}
