package com.brandon.models;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="cartcontent")
@Component
@NoArgsConstructor
public class CartContents {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cartContentId;
	 @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	  @JoinTable(name = "CartContent_Products", 
	             joinColumns = @JoinColumn(name = "Product_Catalog_id"),
	             inverseJoinColumns = @JoinColumn(name = "CartContents_cartcontentid"))
	private ProductModel product;
	private int quantity;

}
