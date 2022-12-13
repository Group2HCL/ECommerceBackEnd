package com.brandon.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int indexNo;
	@OneToOne(targetEntity=Users.class)
	private Users Customer;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private double zipCode;
	 

}