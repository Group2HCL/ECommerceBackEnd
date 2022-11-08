package com.brandon.security.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brandon.models.ProductModel;

public interface ProductService {

	Optional<ProductModel> findById(Long id);
	Page<ProductModel> findAllProductsPageable(Pageable pageable);
}
