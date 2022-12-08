package com.brandon.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brandon.models.ProductModel;
import com.brandon.repositories.ProductRepo;
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials="true")
@RestController
@RequestMapping("/api/Public")

public class PublicController {
	@Autowired
	ProductRepo productRepository;

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
}
