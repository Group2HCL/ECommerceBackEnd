package com.brandon.security.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.brandon.models.OrderProduct;
import com.brandon.models.ProductModel;
import com.brandon.repositories.OrderProductRepo;
import com.brandon.web.OrderProductBean;

@Service
public class OrderProductService {
	@Autowired
	OrderProductRepo repo;
	@Autowired
	ProductService prepo;
	
	
	public OrderProduct createEntry(OrderProductBean product){		
		OrderProduct orderProduct=new OrderProduct(product);
		repo.save(orderProduct);		
		return orderProduct;		
	}
	public void deleteEntry(OrderProduct orderProduct) {
		repo.delete(orderProduct);
	}
}
