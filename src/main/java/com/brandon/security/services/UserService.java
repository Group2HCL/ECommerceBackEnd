package com.brandon.security.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.brandon.models.Users;
import com.brandon.repositories.UserRepo;
@Service
public class UserService {
	@Autowired
	UserRepo repo;
	
	public Users getCurrentUser() {
		Users user = repo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		return user;
	}
	
	public Optional<Users> findByUsername(String username){
	List<Users> roster =repo.findAll();
	Optional<Users> tryUser = null;	
     for(Users user:roster) {
    	 if (username.equals(user.getUsername())) {
    		 tryUser=Optional.of(user);  	 }
     }
     return tryUser;		
	}
	
	public Optional<Users> findByEmail(String email){
		List<Users> roster =repo.findAll();
		Optional<Users> tryUser = null;	
	     for(Users user:roster) {
	    	 if (email.equals(user.getUsername())) {
	    		 tryUser=Optional.of(user);  	 }
	     }
	     return tryUser;		
		};
	}


