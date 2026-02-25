package com.example.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.entity.Production;

public interface ProductionRepository
		extends JpaRepository<Production, Long> {
	
}
