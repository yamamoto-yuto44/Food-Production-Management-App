package com.example.foodapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 原料名
	@Column(name = "material_name", nullable = false, unique = true)
	private String materialName;

	// 現在庫数
	@Column(name = "stock_quantity", nullable = false)
	private Integer stockQuantity;

	// 安全在庫
	@Column(name = "safety_stock_quantity", nullable = false)
	private Integer safetyStockQuantity;

	// 作成日（登録日時）
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	// 更新日（在庫更新など）
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	//コンストラクタ生成
	public Material(String materialName,
			Integer initialStockQuantity,
			Integer safetyStockQuantity) {

		if (materialName == null || materialName.isBlank()) {
			throw new IllegalArgumentException("原料名は必須です");
		}

		if (initialStockQuantity == null || initialStockQuantity < 0) {
			throw new IllegalArgumentException("初期在庫は0以上");
		}

		if (safetyStockQuantity == null || safetyStockQuantity < 0) {
			throw new IllegalArgumentException("安全在庫は0以上");
		}

		this.materialName = materialName;
		this.stockQuantity = initialStockQuantity;
		this.safetyStockQuantity = safetyStockQuantity;
	}

	public void changeStockQuantity(int diff) {

		int newStockQuantity = this.stockQuantity + diff;

		if (newStockQuantity < 0)
			throw new IllegalStateException("在庫不足");

		this.stockQuantity = newStockQuantity;
		this.updatedAt = LocalDateTime.now();
	}

	public void increaseStockQuantity(int stockQuantity) {
		if (stockQuantity <= 0) {
			throw new IllegalArgumentException("増加数量は1以上");
		}
		changeStockQuantity(stockQuantity);
	}

	public void decreaseStockQuantity(int stockQuantity) {
		if (stockQuantity <= 0) {
			throw new IllegalArgumentException("減少数量は1以上");
		}
		changeStockQuantity(-stockQuantity);
	}

	public boolean isBelowSafetyStockQuantity() {

		return this.stockQuantity <= this.safetyStockQuantity;
	}

	public MaterialStockHistory createHistory(int quantity, ChangeType type) {
		return new MaterialStockHistory(this, quantity, type);
	}

}
