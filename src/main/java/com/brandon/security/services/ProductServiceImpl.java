package com.brandon.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.brandon.models.ProductModel;
import com.brandon.repositories.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepo productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepo productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Page<ProductModel> findAllProductsPageable(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
	
	@Override
	public Optional<ProductModel> findById(Long id) {
		return productRepository.findById(id);
	}

}
