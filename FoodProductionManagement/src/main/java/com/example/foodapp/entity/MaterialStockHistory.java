package com.example.foodapp.entity;

import java.time.LocalDateTime;

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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_stock_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaterialStockHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "material_id")
	private Material material;

	@Column(name = "change_date", nullable = false)
	private LocalDateTime changeDate;

	@Column(name = "stock_quantity", nullable = false)
	private Integer stockQuantity;

	@Enumerated(EnumType.STRING)
	@Column(name = "change_type", nullable = false)
	private ChangeType changeType;

	public MaterialStockHistory(Material material,
            Integer stockQuantity,
            ChangeType changeType) {

this.material = material;
this.stockQuantity = stockQuantity;
this.changeType = changeType;
this.changeDate = LocalDateTime.now();
}

}
