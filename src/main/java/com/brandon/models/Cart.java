package com.brandon.models;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="cart")
@Component
@NoArgsConstructor
public class Cart {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cartId;
	private long ownerId;
	 @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	 @JoinTable(name = "Cart_CartContents", 
	             joinColumns = @JoinColumn(name = "Cart_cartid"),
	             inverseJoinColumns = @JoinColumn(name = "CartContent_cartContentId"))
	private Set<CartContents> items=new HashSet<CartContents>();

 public Cart(long ownerId) {
	 this.ownerId=ownerId;
 }
 
}