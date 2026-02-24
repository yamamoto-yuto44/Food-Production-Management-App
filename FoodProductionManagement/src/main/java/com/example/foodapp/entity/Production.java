package com.example.foodapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "production")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Production {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 生産した製品
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	// 生産日
	@Column(name = "production_date", nullable = false)
	private LocalDate productionDate;

	// 生産数
	@Column(nullable = false)
	private Integer quantity;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	// コンストラクタ（新規作成用）
	public Production(Product product, LocalDate date, Integer quantity) {
		if (product == null)
			throw new IllegalArgumentException("商品名は必須です");
		if (date == null)
			throw new IllegalArgumentException("製造日は必須です");
		if (quantity == null || quantity <= 0)
			throw new IllegalArgumentException("数量は1以上にしてください");

		this.product = product;
		this.productionDate = date;
		this.quantity = quantity;
	}

	public void update(Product product, LocalDate date, Integer quantity) {

		if (product == null)
			throw new IllegalArgumentException("商品名は必須です。");

		if (date == null)
			throw new IllegalArgumentException("製造日は必須です。");

		if (quantity == null || quantity <= 0)
			throw new IllegalArgumentException("数量は1以上にしてください。");

		this.product = product;
		this.productionDate = date;
		this.quantity = quantity;
	}
	
	 // Getter は必要に応じて lombok や手動で追加
	public Long getId() { return id; }
    public Product getProduct() { return product; }
    public LocalDate getProductionDate() { return productionDate; }
    public Integer getQuantity() { return quantity; }
	
	public void updateQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
