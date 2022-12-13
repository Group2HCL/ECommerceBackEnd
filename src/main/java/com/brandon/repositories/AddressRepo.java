package com.brandon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brandon.models.Address;
@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {


}
