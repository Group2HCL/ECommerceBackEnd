package com.brandon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brandon.models.ProductModel;
import com.brandon.models.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long>{
}
