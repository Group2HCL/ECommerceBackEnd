package com.brandon.controllers;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.brandon.Exceptions.NotEnoughProductsInStockException;
import com.brandon.models.Orders;
import com.brandon.models.OrderProduct;

import com.brandon.models.ProductModel;
import com.brandon.models.Users;
import com.brandon.repositories.ProductRepo;
import com.brandon.security.services.EmailService;
import com.brandon.security.services.OrderProductService;
import com.brandon.security.services.OrderService;
import com.brandon.security.services.ProductService;
import com.brandon.security.services.ShoppingCartServiceImpl;
import com.brandon.security.services.UserService;
import com.brandon.web.OrderProductBean;


@RestController
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials="true")
@RequestMapping("api/ShoppingCart")
public class ShoppingController {
	
	@Autowired
	ProductRepo prodCon;
	@Autowired
	OrderService orderCon;
	@Autowired
	OrderProductService orderProdCon;
	@Autowired
	UserService usercon;
	@Autowired
	ShoppingCartServiceImpl shopCon;
	@Autowired
	EmailService emailService;
		
	@PostMapping("/checkout")
    public  Map<String, Integer> checkout(@RequestBody Set<OrderProductBean> orderCart) {
		//before the try block is responsible for converting from data the front end like data the backend likes
		HashSet<OrderProduct> itemsOrder = new HashSet<>();
		
		//Understand this will save every product of every attempted order but this is concise.
		for(OrderProductBean product: orderCart) {
			System.out.println(product);
			itemsOrder.add(orderProdCon.createEntry(product));
		}
		//this will attempt to build the order using a set of OrderProducts as constructor arguments
		try{shopCon.checkout(itemsOrder);}
		catch(NotEnoughProductsInStockException e) {
			System.out.println(e);
			return null;
		}
		//I do not return the order that is made to ensure the order is accessible
		//use the user collected from the security context to look for the order then collect the contents of the order
		//
		Users currentUser = usercon.getCurrentUser();
		Optional<Orders> newOrder = orderCon.findOrder(currentUser.getId());
		HashMap<String,Integer> orderItenerary = new HashMap<>();
		if(newOrder.isPresent()) {
			for(OrderProduct orderProduct:newOrder.get().getProducts()) {
				orderItenerary.put(orderProduct.getName(), orderProduct.getQuant());				
			}
		}
		String message="";
		for(Map.Entry<String, Integer> entry: orderItenerary.entrySet()) {
			message=message + entry.getKey() + " " + entry.getValue() +"\n";			
		}
		NumberFormat currency=NumberFormat.getCurrencyInstance(Locale.US);
		String subtotal = currency.format(newOrder.get().getSubtotal());
		System.out.println(message);
		emailService.sendSimpleMessage(currentUser.getEmail(), "Registration@test.com", "Thanks for Ordering!", "Thank you for Ordering!" +"\n"+ message +"\n"+subtotal);
		return orderItenerary;
	}
}
