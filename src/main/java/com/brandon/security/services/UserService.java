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
	
		public boolean existsByUsername(String username) {
			if(repo.findByUsername(username).isPresent()) {
				return true;
			}else {return false;}
			
		}
		public boolean existsByEmail(String email) {
			if(repo.findByEmail(email).isPresent()) {
				return true;
			}else {return false;}
		}
		public void create(Users user) {
			repo.save(user);
		}
		public void delete(Users user) {
			repo.delete(user);
		}
		public void update(Users user) {
			repo.save(user);
		}
		public Optional<Users> findUserByEmail(String email){
			return repo.findByEmail(email);
		}
		public Optional<Users> findByUsername(String name){
			return repo.findByUsername(name);
		}
		public Users retrieve(Long id) {
			Users user = repo.getReferenceById(id);
			return user;
		}
	}


