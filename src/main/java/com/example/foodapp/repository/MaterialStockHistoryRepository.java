package com.example.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.entity.Material;
import com.example.foodapp.entity.MaterialStockHistory;

public interface MaterialStockHistoryRepository extends JpaRepository<MaterialStockHistory, Long> {

	// 原料ごとの履歴を日付降順で取得
	List<MaterialStockHistory> 
	findByMaterialIdOrderByChangeDateDesc(Long materialId);

	List<MaterialStockHistory>
    findByMaterialOrderByChangeDateDesc(Material material);

}
