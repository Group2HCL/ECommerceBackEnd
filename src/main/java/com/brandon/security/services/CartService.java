package com.brandon.security.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandon.Exceptions.NotEnoughProductsInStockException;
import com.brandon.models.Cart;
import com.brandon.models.CartContents;
import com.brandon.models.Order;
import com.brandon.models.OrderProduct;
import com.brandon.models.ProductModel;
import com.brandon.models.Users;
import com.brandon.repositories.CartRepo;
import com.brandon.repositories.ProductRepo;
@Service
public class CartService {
	@Autowired
	CartRepo repo;
	@Autowired
	OrderService orderService;
	@Autowired
	EmailService emailService;
	@Autowired
	ProductRepo productRepository;
	@Autowired
	OrderProductService orderProductService;
	@Autowired
	UserService userService;
	
	public void deleteCart(Cart cart) {
		repo.delete(cart);
	}
	public void createCart(Long owner) {
		Cart cart=new Cart(owner);
		repo.save(cart);
		}
	public void updateCart(Cart cart) {
		repo.save(cart);
	}
	public Optional<Cart> findCart(Long user) {
		Optional<Cart> trycart = Optional.empty();
		try {
			List<Cart> roster = repo.findAll();
		for(Cart cart:roster) {
			if (cart.getOwnerId()==user) {
				trycart=Optional.of(cart);
			}
		}}catch(NullPointerException e) {
			System.out.println("No carts exist");
		}
		return trycart;
	}
	 public void checkout() throws NotEnoughProductsInStockException {
		 	Users uName = userService.getCurrentUser();		 
	        Order order=orderService.createNewOrder(uName.getUsername());
	        Optional<Cart> cart=findCart(uName.getId());
	        ProductModel product=null;
	        if(cart.isPresent()&&!cart.get().getItems().isEmpty()) {
	        for (CartContents contents : cart.get().getItems()) {
	            // Refresh quantity for every product before checking
	            product = productRepository.getReferenceById(contents.getProduct().getId());
	            if (product.getStock() < contents.getQuantity())
	                throw new NotEnoughProductsInStockException(product);
	            OrderProduct newProduct= orderProductService.createEntry(contents.getProduct(), contents.getQuantity());
	            order.getProducts().add(newProduct);
	            product.setStock(contents.getProduct().getStock() - contents.getQuantity());         
	        }
	        String message ="";
	        for(OrderProduct oproduct:order.getProducts()) {message =message + oproduct.getName()+ " Quantity: " + oproduct.getQuant() + "\n";}
	        orderService.saveOrder(order);
	        emailService.sendSimpleMessage(uName.getEmail(), "Registration@test.com", "Order Successful!", "Your Order has been recieved" + message);
	        for (CartContents content:cart.get().getItems()) {productRepository.save(content.getProduct());}      	
	        productRepository.flush();
	        cart.get().getItems().clear();
	        repo.delete(cart.get());
	        }
	    }
}
