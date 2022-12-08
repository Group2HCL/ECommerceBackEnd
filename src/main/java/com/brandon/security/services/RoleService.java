package com.brandon.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandon.models.Roles;
import com.brandon.models.UserRoles;
import com.brandon.models.Users;
import com.brandon.repositories.RoleRepo;
import com.brandon.repositories.UserRepo;


@Service
public class RoleService {

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserRepo userRepo;


	// Create a role
	public Roles createRole(Roles r) {
		return roleRepo.save(r);
	}

	// Get all roles
	public List<Roles> getAllRoles() {
		return roleRepo.findAll();
	}

	// Get by role ID
	public Roles getByRoleId(int roleId) {
		return roleRepo.findById(roleId).get();
	}

	// Delete by role ID
	public void deleteByRoleId(int roleId) {
		roleRepo.deleteById(roleId);
	}
	
	

	// Admin rights
	public boolean updateAdminRights(Long userId) {
		Optional<Users> targetUser = userRepo.findById(userId);
		boolean isAdmin = false;
		// Checks if there's a user with the given ID
		if (targetUser.isPresent()) {
			if (!(targetUser.get().getRoles().contains(roleRepo.findByName(UserRoles.ROLE_ADMIN).get()))) {
				Set<Roles> targetUserRoles = targetUser.get().getRoles();
				targetUserRoles.add(roleRepo.findByName(UserRoles.ROLE_ADMIN).get());
				targetUser.get().setRoles(targetUserRoles);
				userRepo.save(targetUser.get());
				isAdmin = true;
			} else {
				Set<Roles> targetUserRoles = targetUser.get().getRoles();
				targetUserRoles.remove(roleRepo.findByName(UserRoles.ROLE_ADMIN).get());
				targetUser.get().setRoles(targetUserRoles);
				userRepo.save(targetUser.get());
			}
		} 
		return isAdmin;
	}
}
