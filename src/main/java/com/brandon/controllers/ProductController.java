            package com.brandon.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brandon.models.ProductModel;
import com.brandon.repositories.ProductRepo;
import com.brandon.security.services.ProductService;

@CrossOrigin (origins = "http://localhost:4200/", allowCredentials="true")
@RestController
@RequestMapping("/api/Product")
public class ProductController {
	@Autowired
	ProductRepo productRepository;
	ProductService pservice;
	
	@GetMapping("/products")
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<ProductModel>> getAllProducts(@RequestParam(required = false) String name){
		//try {
			List<ProductModel>products = new ArrayList<ProductModel>();
			
			if (name ==null)
				productRepository.findAll().forEach(products::add);
			else
				productRepository.findByNameContaining(name).forEach(products::add);
			System.out.println("Content of products is " + products.toString());
			if(products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(products,HttpStatus.OK);
//		}catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
//	}
	
	@GetMapping("/products/{id}")
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ProductModel> getProductById(@PathVariable("id")long id){
		Optional<ProductModel> productData = productRepository.findById(id);
		
		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/products")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel product){
		try {
			ProductModel product1 = productRepository
					.save(new ProductModel(product.getName(),product.getCategory(),product.getPrice(),product.getStock(),product.getDescription()));
			return new ResponseEntity<>(product1,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/products/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductModel> updateProduct(@PathVariable("id") long id,@RequestBody ProductModel product){
		Optional<ProductModel> productData = productRepository.findById(id);
		
		if (productData.isPresent()) {
			ProductModel product2 = productData.get();
			product2.setName(product.getName());
			product2.setCategory(product.getCategory());
			product2.setPrice(product.getPrice());
			product2.setStock(product.getStock());
			product2.setDescription(product.getDescription());
			return new ResponseEntity<>(productRepository.save(product2),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/products/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id){
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/products")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteAllProducts(){
		try {
			productRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
