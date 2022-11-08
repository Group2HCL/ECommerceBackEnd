package com.brandon.security.services;

import java.util.Optional;

import com.brandon.models.Users;

public interface UserService {
	Optional<Users> findByUsername(String username);
	
	Optional<Users> findByEmail(String email);
	
	Users saveUser(Users user);

}
