package com.brandon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brandon.models.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{

}
