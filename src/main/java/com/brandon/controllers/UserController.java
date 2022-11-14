package com.brandon.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brandon.models.UserRoles;
import com.brandon.models.Users;
import com.brandon.repositories.RoleRepo;
import com.brandon.repositories.UserRepo;
import com.brandon.security.services.EmailService;
import com.brandon.security.services.UserService;

@CrossOrigin (origins = "http://localhost:4200/", allowCredentials="true")
@RestController
@RequestMapping("/api/User")
public class UserController {
	@Autowired
	UserRepo userRepository;
	@Autowired
	UserService uService;
	@Autowired
	RoleRepo roleRepository;
	
	@GetMapping("/users")
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Users>> getAllUsers(@RequestParam(required = false) String username){
		try {
			List<Users>users = new ArrayList<Users>();
			
			if (username ==null)
				userRepository.findAll().forEach(users::add);
			else
				users.add(uService.findByUsername(username).get());
			
			if(users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{id}")
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Users> getUserById(@PathVariable("id")long id){
		Optional<Users> userData = userRepository.findById(id);
		
		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("users")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Users> createUser(@RequestBody Users user){
		try {
			Users user1 = userRepository
					.save(new Users(user.getUsername(),user.getEmail(),user.getPassword()));			
			return new ResponseEntity<>(user1,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/users/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Users> updateUser(@PathVariable("id") long id,@RequestBody Users user){
		Optional<Users> userData = userRepository.findById(id);
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		if (userData.isPresent()) {
			Users user2 = userData.get();
			user2.setUsername(user.getUsername());
			user2.setEmail(user.getEmail());
			user2.setPassword(passwordEncoder.encode(user.getPassword()));
			return new ResponseEntity<>(userRepository.save(user2),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/users/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id){
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteAllUsers(){
		try {
			userRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/toggle/{id}")
	public void toggleUser(@PathVariable long id) {
		Optional<Users> targetUser= userRepository.findById(id);
		if(targetUser.isPresent()) {
			if(!targetUser.get().getRoles().contains(roleRepository.findByName(UserRoles.ROLE_ADMIN).get()));
			targetUser.get().getRoles().add(roleRepository.findByName(UserRoles.ROLE_ADMIN).get());
		}else {
			targetUser.get().getRoles().remove(roleRepository.findByName(UserRoles.ROLE_ADMIN).get());
		}
	}

}

