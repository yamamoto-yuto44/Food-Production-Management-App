package com.example.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {

	// 原料名の重複防止
	boolean existsByMaterialName(String materialName);

}
