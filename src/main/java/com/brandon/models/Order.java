package com.brandon.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

import lombok.Data;

@Entity
@Data
@Table(name="Order")
public class Order {
	@Id
	@Column(name="orderno")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderno;
	 @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	 @JoinTable(name = "Order_OrderProduct", 
	             joinColumns = @JoinColumn(name = "Order"),
	             inverseJoinColumns = @JoinColumn(name = "OrderProduct"))
	private Set<OrderProduct> products=new HashSet<OrderProduct>();
	 @Column(name="subtotal")
	private BigDecimal subtotal;
	 @Column(name="customerNo")
	private long customerNo;
	 @Column(name="status")
	private String status;

	public Order() {
		this.status="Pending";
	}
}
