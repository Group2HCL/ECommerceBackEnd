package com.brandon.security.services;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.brandon.Exceptions.NotEnoughProductsInStockException;
import com.brandon.models.Orders;
import com.brandon.models.OrderProduct;
import com.brandon.models.ProductModel;
import com.brandon.models.Users;
import com.brandon.repositories.ProductRepo;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	private final ProductRepo productRepository;
	@Autowired
	private OrderService orderService;
	
	private String uName=SecurityContextHolder.getContext().getAuthentication().getName();
	
	private Map<ProductModel, Integer> products = new HashMap<>();
	
    @Autowired
    public ShoppingCartServiceImpl(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }
    /* The Majority of this Service is defunct. It has been left to prevent unforseen issues and 
     * in interest of time and the low perceived value of code cleanup.
     * It is anticipated the only valuable function here is the order creation
     * @param product
     */
    @Override
    public void addProduct(ProductModel product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
    @Override
    public void removeProduct(ProductModel product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<ProductModel, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */
    @Override
    public void checkout(Set<OrderProduct> itemsOrdered) throws NotEnoughProductsInStockException {
        Orders orders=orderService.createNewOrder(uName);        
        List<ProductModel> productInventory = productRepository.findAll();
        
        for (OrderProduct entry : itemsOrdered) {
            // Refresh quantity for every product before checking
        	//idBased retrieval behaving unexpectedly with no clear cause. changing to name based retrieval
        	ProductModel product = null;
            for(ProductModel search:productInventory) {
            	//line 101(check stock logic) reporting product as null when it shouldn't be possible. this is report for find
            	System.out.println("testing "+search.getName()+ " with "+entry.getName());
            	
        	   if(search.getName().equals(entry.getName())) {
        		   //this is the report for match behavior
        		   System.out.println("match Found");       		   
        		   product=search;
        	   }
        	   }
            if (product.getStock() < entry.getQuant())
            	
                throw new NotEnoughProductsInStockException(product);                      
            orders.getProducts().add(entry);
            product.setStock(product.getStock() - entry.getQuant());
            productRepository.save(product);
        }
        orders.setSubtotal(getTotal(orders));
        orderService.saveOrder(orders);        
        productRepository.saveAll(products.keySet());
        productRepository.flush();
        products.clear();
    }

    @Override
    public BigDecimal getTotal(Orders order) {
        return order.getProducts().stream()
                .map(entry -> entry.getPrice().multiply(BigDecimal.valueOf(entry.getQuant())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
