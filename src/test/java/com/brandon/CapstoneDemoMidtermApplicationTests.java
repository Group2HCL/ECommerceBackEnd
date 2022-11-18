package com.brandon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.brandon.web.LoginBean;
import com.brandon.web.SignUpBean;
import com.brandon.web.UserInfoBean;
import com.brandon.controllers.AuthenticationController;
import com.brandon.models.Roles;
import com.brandon.models.Users;
import com.brandon.repositories.UserRepo;
import com.brandon.security.services.UserService;

@SpringBootTest
class CapstoneDemoMidtermApplicationTests {
	@Autowired
	AuthenticationController authController;
	@Autowired
	UserService uService;

	@Test
	void contextload() {}
	/*@Test
	void newUserProcess() {
	SignUpBean newUser = new SignUpBean();
	newUser.setEmail("test@testing.com");
	newUser.setUsername("testman");
	newUser.setPassword("123");
	ResponseEntity<?> loginStatus=authController.registerUser(newUser);
	assertEquals(HttpStatus.OK,loginStatus.getStatusCode(),"register fail");
	LoginBean newLogin =new LoginBean(newUser.getUsername(),newUser.getPassword());
	ResponseEntity<?> loginResult=authController.authenticateUser(newLogin);
	assertEquals(loginResult.getStatusCode(),HttpStatus.OK, "test Login not OK");
	//UserInfoBean responseDetails = loginResult.getBody();
	//assertEquals(responseDetails.getEmail(),newUser.getEmail(),"DB entry not readable");
	Users testUser=uService.findByUsername("testman").get();
	uService.delete(testUser);
	System.out.println("New user can register,and login");
	}
	*/
	//@Test
	//void adminProcess() {
		
	//}
	
	

}
