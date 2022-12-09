package com.brandon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.brandon.models.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer>{

}
