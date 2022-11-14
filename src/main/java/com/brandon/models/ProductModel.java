package com.brandon.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@Entity
@Table(name="Product_Catalog")
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="category")
	private String category;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name ="stock")
	private long stock;
	
	@Column(name ="description")
	private String description;
	
	@Override 
	public String toString() {
		
		return "Product [ID= " + id + ", Name= " + name + " , Category= " + ", Price= " + price + ", Stock = " + stock + ", Description: " + description + "]";
	}

	public ProductModel(String name, String category, BigDecimal price, long stock, String description) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.stock = stock;
		this.description = description;
	}
	public ProductModel() {
		this.id=Long.valueOf(0);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		ProductModel product = (ProductModel) o;
		
		return id.equals(product.id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
