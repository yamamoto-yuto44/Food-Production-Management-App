package com.example.foodapp.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "material_id", nullable = false)
	private Material material;

	@Column(nullable = false)
	private Integer orderQuantity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	@Column(nullable = false)
	private LocalDate orderDate;

	private LocalDate receivedDate;

	// 発注登録用
	public Order(Material material, Integer orderQuantity) {
		if (orderQuantity == null || orderQuantity <= 0) {
			throw new IllegalArgumentException("発注数は1以上");
		}
		this.material = material;
		this.orderQuantity = orderQuantity;
		this.status = OrderStatus.ORDERED;
		this.orderDate = LocalDate.now();
	}

	// 入荷処理
	public void markAsReceived() {
		if (this.status != OrderStatus.ORDERED) {
			throw new IllegalStateException("既に処理済みです");
		}
		this.status = OrderStatus.RECEIVED;
		this.receivedDate = LocalDate.now();
	}

	public void cancel() {
		if (this.status != OrderStatus.ORDERED) {
			throw new IllegalStateException("この発注はキャンセルできません");
		}
		this.status = OrderStatus.CANCELED;
	}
}
