package com.brandon.security.services;

import java.math.BigDecimal;
import java.util.Map;

import com.brandon.Exceptions.NotEnoughProductsInStockException;
import com.brandon.models.ProductModel;

public interface ShoppingCartService {

		void addProduct(ProductModel product);
		
		void removeProduct(ProductModel product);
		
		Map<ProductModel, Integer> getProductsInCart();
		
		void checkout() throws NotEnoughProductsInStockException;
		
		BigDecimal getTotal();
}
