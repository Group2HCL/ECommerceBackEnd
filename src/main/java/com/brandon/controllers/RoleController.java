package com.brandon.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandon.models.Roles;
import com.brandon.models.Users;
import com.brandon.security.services.RoleService;
import com.brandon.security.services.UserService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserProfile;


@RestController
@RequestMapping("api/roles")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials="true")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	Client client;
	

	@GetMapping("/")
	public List<Roles> getRoles() {
		return roleService.getAllRoles();
	}

	@PostMapping("/")
	public Roles createRole(@RequestBody Roles r) {
		return roleService.createRole(r);
	}
	
	@DeleteMapping("/{roleId}")
	public void deleteRole(@PathVariable int roleId) {
		roleService.deleteByRoleId(roleId);
	}

	
	@GetMapping("/toggle/{userId}")
    public boolean toggleUser(@PathVariable Long userId) {		
		return roleService.updateAdminRights(userId); 
    } 
}
