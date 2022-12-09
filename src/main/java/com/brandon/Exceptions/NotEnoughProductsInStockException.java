package com.brandon.Exceptions;

import com.brandon.models.ProductModel;

public class NotEnoughProductsInStockException extends Exception {
	
	private static final String DEFAULT_MESSAGE = "Not enough products in stock";
	
	public NotEnoughProductsInStockException() {
		super(DEFAULT_MESSAGE);
	}
	
	public NotEnoughProductsInStockException(ProductModel product) {
		super(String.format("Not enough %s products in stock. Only %d left", product.getName(), product.getStock()));
	}
}
