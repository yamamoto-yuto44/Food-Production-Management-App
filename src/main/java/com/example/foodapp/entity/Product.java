package com.example.foodapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity //DBに保存するため、自動insertなど
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_name", nullable = false, unique = true, length = 20)
	private String productName;
	//DBでいう商品名(product_name)

	// 作成日は一生固定なので、UPDATE時に変更・未記入不可設定
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	//DBでいう作成日(created_at)

	// 更新日は更新時・作成時に毎回変更なので、未記入不可だけ設定
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	//DBでいう更新日(updated_at)

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	public Product(String productName) {
        this.productName = productName;
    }

}
