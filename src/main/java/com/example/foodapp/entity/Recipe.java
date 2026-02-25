package com.example.foodapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe", uniqueConstraints = { @UniqueConstraint(columnNames = { "product_id", "material_id" }) })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// どの製品のレシピか
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//いつデータを取りに行くか LAZY=必要になったときに
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	// どの原料を使うか
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "material_id", nullable = false)
	private Material material;
	
	// 製品1個あたりの原料使用量
	@Column(name = "required_quantity", nullable = false)
	private Integer requiredQuantity;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	// マスタ登録用コンストラクタ
	public Recipe(Product product, Material material, Integer requiredQuantity) {
		if (requiredQuantity == null || requiredQuantity <= 0) {
			throw new IllegalArgumentException("requiredQuantity は正の数である必要があります");
		}
		this.product = product;
		this.material = material;
		this.requiredQuantity = requiredQuantity;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
