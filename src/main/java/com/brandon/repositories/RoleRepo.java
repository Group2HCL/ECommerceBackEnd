package com.brandon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brandon.models.Roles;
import com.brandon.models.UserRoles;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Long>{
	Optional<Roles> findByName(UserRoles name);
	
	//Roles findByRole(@Param("role") String role);
}
