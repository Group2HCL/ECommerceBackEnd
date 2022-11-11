package com.brandon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.brandon.security.services.RoleService;


@RestController
@RequestMapping("api/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {
	@Autowired
	private RoleService roleService;
	

	@GetMapping("/")
	public List<Roles> getRoles() {
		return roleService.getAllRoles();
	}

	@PostMapping("/")
	public Roles createRole(@RequestBody Roles r) {
		return roleService.createRole(r);
	}
	
	@DeleteMapping("/{roleId}")
	public void deleteRole(@PathVariable Long roleId) {
		roleService.deleteByRoleId(roleId);
	}

	
	@GetMapping("/toggle/{userId}")
    public boolean toggleUser(@PathVariable Long userId) {
		return roleService.updateAdminRights(userId);  
    } 
}
