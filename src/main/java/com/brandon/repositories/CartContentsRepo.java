package com.brandon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brandon.models.CartContents;

@Repository
public interface CartContentsRepo extends JpaRepository<CartContents,Integer>{
	

}
