package com.example.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.entity.Product;

public interface ProductRepository
		extends JpaRepository<Product, Long> {

}
