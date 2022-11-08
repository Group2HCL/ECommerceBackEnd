package com.brandon.web;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class UserInfoBean {
private String token;
private String type = "Bearer";
private Long id;
private String username;
private String email;
private List<String> roles;
public UserInfoBean(String token , Long id, String username, String email, List<String> roles) {
	super();
	this.token = token;
	this.id = id;
	this.username = username;
	this.email = email;
	this.roles = roles;
}


}

