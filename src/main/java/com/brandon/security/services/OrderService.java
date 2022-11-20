package com.brandon.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandon.models.Orders;
import com.brandon.models.Users;
import com.brandon.repositories.OrderRepo;
import com.brandon.repositories.UserRepo;

@Service
public class OrderService {
	@Autowired
	OrderRepo repo;
	@Autowired
	UserService uService;
	
	public Orders createNewOrder(String uName) {
		Users user=uService.findByUsername(uName).get();
		Orders newOrder = new Orders();
		newOrder.setCustomerNo(user.getId());
		return newOrder;
	}
	public void saveOrder(Orders orders) {
		repo.save(orders);
	}
	
	public Optional<Orders> findOrder(Long id){
		Optional<List<Orders>> order=Optional.of(repo.findAll());
		Optional<Orders> hopefulOrder=Optional.empty();
		if(order.isPresent()) {
		 for(Orders orders: order.get()) {
			if(id==orders.getCustomerNo()) {
				hopefulOrder=Optional.of(orders);
			}
		}}
		return hopefulOrder;
		
	}
	
}
