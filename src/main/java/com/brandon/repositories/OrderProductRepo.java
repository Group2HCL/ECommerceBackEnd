package com.brandon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.brandon.models.OrderProduct;
@Repository
public interface OrderProductRepo extends JpaRepository<OrderProduct, Integer> {

}
