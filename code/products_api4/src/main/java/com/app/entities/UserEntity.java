package com.app.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * User - class
id : Long
first name , last name, email ,password - String
dob - LocalDate
role : Enum 
image : byte[]
 */
/*
 * Mandatory annotation for hibernate to manage entity 
 * @Entity - class level 
 * @Id - for PK (unique id property - typically - ref type for easy null 
 * checking) - field level | getter level
 */
//JPA package
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity // mandatory
@Table(name = "secure_users") // name of the table
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = { "password"}, callSuper = true)
public class UserEntity extends BaseEntity implements UserDetails{

	// col name first_name , varchar(20)
	@Column(name = "first_name", length = 20)
	private String firstName;
	@Column(name = "last_name", length = 30)
	private String lastName;
	// col name - email , len=30 , unique constraint
	@Column(length = 30, unique = true)
	private String email;
	// col name - password , len=20 ,not null constraint
	@Column(length = 300, nullable = false)
	private String password;
	// col name dob , type : date
	private LocalDate dob;
	// col - enum , name=user_role
	// to store enum constants
	@Enumerated(EnumType.STRING)
	@Column(name = "user_role")
	private UserRole role;
	
	public UserEntity(String firstName, String lastName, String email, String password, LocalDate dob, UserRole role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(this.role.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	

	
}
