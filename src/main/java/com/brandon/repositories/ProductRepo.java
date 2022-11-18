package com.brandon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandon.models.ProductModel;
import com.brandon.models.Users;

public interface ProductRepo extends JpaRepository<ProductModel,Long> {
	List<ProductModel> findByNameContaining(String name);
}
