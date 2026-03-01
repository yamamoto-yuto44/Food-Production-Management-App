package com.example.foodapp.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialForm {

	@NotBlank(message = "原料名を入力してください")
	private String materialName;

	@NotNull(message = "在庫数を入力してください")
	@Min(value = 0, message = "在庫数は0以上で入力してください")
	private Integer stockQuantity;

	@NotNull(message = "安全在庫数を入力してください")
	@Min(value = 0, message = "安全在庫数は0以上で入力してください")
	private Integer safetyStockQuantity;
}
