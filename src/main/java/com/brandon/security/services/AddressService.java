package com.brandon.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandon.models.Address;
import com.brandon.repositories.AddressRepo;

@Service
public class AddressService {
	@Autowired
	AddressRepo addressRepo;
	
	
	public void createAddress(Address address) {
		addressRepo.save(address);
	}
	public void deleteAddress(Address address) {
		addressRepo.delete(address);
	}
	public void updateAddress(Address address) {
		addressRepo.save(address);
	}
	public Optional<Address> retrieveAddress(int id) {
		return addressRepo.findById(id);
	}
}
