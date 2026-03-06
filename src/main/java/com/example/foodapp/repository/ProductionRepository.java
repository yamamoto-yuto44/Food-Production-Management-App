package com.example.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.entity.Production;

public interface ProductionRepository
		extends JpaRepository<Production, Long> {
	
	// 製造日 新しい順
    List<Production> findAllByOrderByProductionDateDesc();

	
}
