package com.brandon.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.brandon.models.Cart;
import com.brandon.models.CartContents;
import com.brandon.models.Order;
import com.brandon.models.OrderProduct;
import com.brandon.models.ProductModel;
import com.brandon.models.Users;
import com.brandon.repositories.CartContentsRepo;
import com.brandon.repositories.ProductRepo;
import com.brandon.security.services.CartService;
import com.brandon.security.services.OrderService;
import com.brandon.security.services.ProductService;
import com.brandon.security.services.UserService;


@RestController
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials="true")
@RequestMapping("api/ShoppingCart")
public class ShoppingController {
	@Autowired
	CartService cartCon;
	@Autowired
	ProductRepo prodCon;
	@Autowired
	OrderService orderCon;
	@Autowired
	UserService usercon;
	@Autowired
	CartContentsRepo cartContentCon;
	
	@GetMapping("/shoppingCart")
	public @ResponseBody Map<ProductModel, Integer> shoppingCart() {
		long currentUser = usercon.getCurrentUser().getId();
		Optional<Cart> cart = cartCon.findCart(currentUser);
		HashMap<ProductModel,Integer> items = new HashMap<>();
		try {
		if(cart.isPresent()&&!cart.get().getItems().isEmpty()) {
			for(CartContents content:cart.get().getItems()) {
				items.put(content.getProduct(), content.getQuantity());			
				}
		}}catch (NullPointerException e) {System.out.println("Cart is empty");}
		return items;		
	}
	
	
	@GetMapping("/addProduct/{productId}")
    public @ResponseBody void addProductToCart(@PathVariable("productId") Long productId) {
		//get the current usersID using Security
		Long currentUser= usercon.getCurrentUser().getId();
		Optional<Cart> cart = cartCon.findCart(currentUser);
		ProductModel product = prodCon.getReferenceById(productId);
		boolean loadTrigger = true;
		if(!cart.isPresent()) {
			cartCon.createCart(currentUser);
			System.out.println("Cart synthesized in process");
			cart=cartCon.findCart(currentUser);
			}		
		for (CartContents contents:cart.get().getItems()) {
			System.out.println("Product Found");
			try {	
			if(product.getId()==contents.getProduct().getId()) {
					contents.setQuantity(contents.getQuantity()+1);
					cartContentCon.save(contents);
					System.out.println("Fired on created item");
					loadTrigger=false;
				}}
			catch(NullPointerException e) {System.out.println("Cart is empty");
			break;}
			}
		if (loadTrigger){
				CartContents item=new CartContents();
				item.setProduct(product);
				item.setQuantity(1);
				cart.get().getItems().add(item);
				System.out.println("Fired on new item");
				cartContentCon.save(item);
			}
	
		cartCon.updateCart(cart.get());
    }
	
	@GetMapping("/removeProduct/{productId}")
    public @ResponseBody void removeProductFromCart(@PathVariable("productId") Long productId) {
		Long currentUserId = usercon.getCurrentUser().getId();
		Optional<Cart> cart = cartCon.findCart(currentUserId);
		ProductModel product = prodCon.getReferenceById(productId);
		if(cart.isPresent()&&!cart.get().getItems().isEmpty())
			for(CartContents content:cart.get().getItems()){
				try {	
					if(product.getId()==content.getProduct().getId()) {							
							cartContentCon.delete(content);
							System.out.println("Deleted Item");							
						}}
					catch(NullPointerException e) {System.out.println("Cart is empty");
					break;}
				cartCon.updateCart(cart.get());
			}
    	}	
	@GetMapping("/checkout")
    public @ResponseBody Map<String, Integer> checkout() {
		try{cartCon.checkout();}
		catch(NotEnoughProductsInStockException e) {
			return null;
		}
		Users currentUser = usercon.getCurrentUser();
		Optional<Order> newOrder = orderCon.findOrder(currentUser.getId());
		HashMap<String,Integer> orderItenerary = new HashMap<>();
		if(newOrder.isPresent()) {
			for(OrderProduct orderProduct:newOrder.get().getProducts()) {
				orderItenerary.put(orderProduct.getName(), orderProduct.getQuant());				
			}
		}
		return orderItenerary;
	}
}
