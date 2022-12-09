package com.brandon.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.brandon.web.OrderProductBean;

import lombok.Data;

@Entity
@Data
@Table(name="OrderProduct")
public class OrderProduct {
	/*understand this will mean a duplicate information in the DB but this is the safest way to edit an order
	 * without worrying about editing the store stock by Accident
	 */
		@Id
		@Column(name="entryId") 
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int entryID;

		@Column(name="name")
		private String name;
		
		@Column(name="prodId")
		private long prodId;
		
		@Column(name="category")
		private String category;
		
		@Column(name="price")
		private BigDecimal price;
		
		@Column(name="quant")
		private int quant;

		public OrderProduct(OrderProductBean product) {
			super();
			this.name = product.getName();
			this.category = product.getCategory();
			this.price = BigDecimal.valueOf(product.getPrice());
			this.quant=product.getNum();
			this.prodId=product.getProdId();
			}
		
		
		
		
		
}
