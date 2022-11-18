package com.brandon.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.brandon.models.ProductModel;
import com.brandon.repositories.ProductRepo;

	@Service
	public class ProductService {
		@Autowired
		private ProductRepo productRepository;		
		public List<ProductModel> findbyNameContaining(String name) {
			List<ProductModel> inventory = productRepository.findAll();
			for(ProductModel product:inventory) {
				if(!product.getName().contains(name)) {
					inventory.remove(product);
				}
			}	
			return inventory;
		}
		
		
		public Page<ProductModel> findAllProductsPageable(Pageable pageable) {
			return productRepository.findAll(pageable);
		}
		
		
	
		public Optional<ProductModel> findById(Long id) {
			return productRepository.findById(id);
		}
}