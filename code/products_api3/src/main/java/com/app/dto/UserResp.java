package com.app.dto;

import java.time.LocalDate;

import com.app.entities.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResp extends BaseDTO {
	private String firstName;

	private String lastName;

	private String email;

	private LocalDate dob;

	private UserRole role;
}
