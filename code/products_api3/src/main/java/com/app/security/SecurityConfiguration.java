package com.app.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration // to declare the as java config class
// equivalent to bean config xml file
@EnableWebSecurity // to enable spring sec config in this class
@EnableMethodSecurity // to enable method level security 
@AllArgsConstructor
public class SecurityConfiguration {
	//depcy
	private final PasswordEncoder encoder;
	/*
	 * Configure spring bean (@Bean) to configure spring securtiy filter chain
	 * 
	 */
	@Bean
	SecurityFilterChain configureSecFilterChain(HttpSecurity http) throws Exception {
		// disable CSRF protection : since un necessary with stateless REST APIs
		http.csrf(csrf -> csrf.disable());
		// form login is enabled by default , to disable it
		http.formLogin(form -> form.disable())
				// enable Basic HTTP auth
				.httpBasic(Customizer.withDefaults());
		// add URL based authorization rules
		// un protected end points - swagger , view products
		http.authorizeHttpRequests(
				request -> request.requestMatchers
				("/v*/api-docs/**", "/swagger-ui/**"
						, "/products/view","/users/signup","/users/signin")
				.permitAll()
				//only admin should be allowed to add product
						.requestMatchers(
								"/products/add"/* ,"/products/delete/**" */)
				.hasRole("ADMIN")
				//only customer can purchase the product
				.requestMatchers("/products/purchase/**")
				.hasRole("CUSTOMER")
						// any other request - can accessed only by authenticated users
				.anyRequest().authenticated())
				// tell Spring sec - not to create any HttpSession object
				// to store spring security info
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// HttpSecurity class builds spring sec filter chain , as per above
		// customizations
		return http.build();
	}

	

}
