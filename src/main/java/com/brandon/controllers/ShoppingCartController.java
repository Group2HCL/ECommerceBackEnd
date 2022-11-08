package com.brandon.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.brandon.Exceptions.NotEnoughProductsInStockException;
import com.brandon.models.ProductModel;
import com.brandon.security.services.ProductService;
import com.brandon.security.services.ShoppingCartService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ShoppingCart")
public class ShoppingCartController {
	
	private final ShoppingCartService shoppingCartService;
	
	private final ProductService productService;
	
	@Autowired
	public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService) {
		this.shoppingCartService = shoppingCartService;
		this.productService = productService;
	}
	
	@GetMapping("/shoppingCart")
	public @ResponseBody Map<ProductModel, Integer> shoppingCart() {
		return this.shoppingCartService.getProductsInCart();
	}
	
	@GetMapping("/shoppingCart/price")
	public @ResponseBody String shoppingCartPrice() {
		return this.shoppingCartService.getTotal().toString();
	}
	
	@GetMapping("/shoppingCart/addProduct/{productId}")
    public @ResponseBody void addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
    }
	
	@GetMapping("/shoppingCart/removeProduct/{productId}")
    public @ResponseBody void removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
    }
	
	@GetMapping("/shoppingCart/checkout")
    public @ResponseBody Map<ProductModel, Integer> checkout() {
        try {
            shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            return this.shoppingCart();
        }
        return shoppingCart();
    }

}
