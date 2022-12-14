package com.brandon.web;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpBean {
	@NotBlank
	@Size(min = 3, max = 50)
	private String firstName;
	
	@Size(min =2, max = 50)
	private String lastName;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	private Set<String> role;

	@NotBlank
	@Size(min = 2, max = 40)
	private String password;
	}

