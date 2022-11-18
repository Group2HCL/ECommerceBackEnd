package com.brandon.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandon.models.Order;
import com.brandon.models.Users;
import com.brandon.repositories.OrderRepo;
import com.brandon.repositories.UserRepo;

@Service
public class OrderService {
	@Autowired
	OrderRepo repo;
	@Autowired
	UserService uService;
	
	public Order createNewOrder(String uName) {
		Users user=uService.findByUsername(uName).get();
		Order newOrder = new Order();
		newOrder.setCustomerNo(user.getId());
		return newOrder;
	}
	public void saveOrder(Order order) {
		repo.save(order);
	}
	
	public Optional<Order> findOrder(Long id){
		Optional<List<Order>> orders=Optional.of(repo.findAll());
		Optional<Order> hopefulOrder=Optional.empty();
		if(orders.isPresent()) {
		 for(Order order: orders.get()) {
			if(id==order.getCustomerNo()) {
				hopefulOrder=Optional.of(order);
			}
		}}
		return hopefulOrder;
		
	}
	
}
