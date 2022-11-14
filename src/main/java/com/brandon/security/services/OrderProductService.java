package com.brandon.security.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.brandon.models.OrderProduct;
import com.brandon.models.ProductModel;
import com.brandon.repositories.OrderProductRepo;

@Service
public class OrderProductService {
	@Autowired
	OrderProductRepo repo;
	@Autowired
	ProductService prepo;
	
	
	public OrderProduct createEntry(ProductModel product, int quantity){		
		OrderProduct orderProduct=new OrderProduct(product, quantity);
		repo.save(orderProduct);		
		return orderProduct;		
	}
}
