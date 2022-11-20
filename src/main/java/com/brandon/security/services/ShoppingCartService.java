package com.brandon.security.services;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.brandon.Exceptions.NotEnoughProductsInStockException;
import com.brandon.models.OrderProduct;
import com.brandon.models.Orders;
import com.brandon.models.ProductModel;

public interface ShoppingCartService {

		void addProduct(ProductModel product);
		
		void removeProduct(ProductModel product);
		
		Map<ProductModel, Integer> getProductsInCart();
		
		void checkout(Set<OrderProduct> itemsOrdered) throws NotEnoughProductsInStockException;
		
		BigDecimal getTotal(Orders order);
}
