package com.brandon.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductBean {
	
	private long prodId;
	
	private String name;
	
	private String category;
	
	private double price;
	
	private long stock;
	
	private String description;
	
	private int num;
}
