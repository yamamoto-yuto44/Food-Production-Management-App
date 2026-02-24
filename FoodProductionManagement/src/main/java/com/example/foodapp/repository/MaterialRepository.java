package com.example.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {

	// 原料名で検索（重複防止などに使える）
	boolean existsByMaterialName(String materialName);

}
