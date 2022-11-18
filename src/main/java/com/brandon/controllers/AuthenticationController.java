package com.brandon.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandon.models.Roles;
import com.brandon.models.UserRoles;
import com.brandon.models.Users;
import com.brandon.repositories.RoleRepo;
import com.brandon.repositories.UserRepo;
import com.brandon.security.jwt.JwtUtils;
import com.brandon.security.services.EmailService;
import com.brandon.security.services.UserService;
import com.brandon.security.services.UserDetailsImpl;
import com.brandon.web.LoginBean;
import com.brandon.web.MessageBean;
import com.brandon.web.SignUpBean;
import com.brandon.web.UserInfoBean;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleRepo roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	EmailService emailService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginBean loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new UserInfoBean(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), roles));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageBean("Invalid username or password!"));
		}
	}

	@PostMapping("/verifypw")
	public ResponseEntity<Boolean> verifyPassword(@Valid @RequestBody LoginBean loginRequest) {
		boolean doesMatch = false;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(doesMatch = true);
		} catch (Exception e) {
			return (ResponseEntity<Boolean>) ResponseEntity.ok();
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpBean signUpRequest) {
		if (userService.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageBean("Error: Username is already taken!"));
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageBean("Error: Email is already taken!"));
		}

		Users user = new Users(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Roles> roles = new HashSet<>();

		if (strRoles == null) {
			Roles userRoles = roleRepository.findByName(UserRoles.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRoles);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Roles adminRole = roleRepository.findByName(UserRoles.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;

				default:
					Roles userRole = roleRepository.findByName(UserRoles.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userService.create(user);
		emailService.sendSimpleMessage(user.getEmail(), "Registration@test.com", "Welcome to the Shop!",
				"Thank you for joining the Shop!");

		return ResponseEntity.ok(new MessageBean("User registered successfully!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {

		return ResponseEntity.ok().body(new MessageBean("You've been signed out!"));
	}

}
