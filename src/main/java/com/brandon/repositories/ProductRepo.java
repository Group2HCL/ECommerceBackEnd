package com.brandon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandon.models.ProductModel;

public interface ProductRepo extends JpaRepository<ProductModel,Long> {
	Optional<ProductModel> findById(Long id);
	List<ProductModel> findByNameContaining(String name);
	//ProductModel findOne(Long id);
}
