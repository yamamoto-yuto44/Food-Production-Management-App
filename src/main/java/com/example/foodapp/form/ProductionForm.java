package com.example.foodapp.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionForm {

	@NotNull(message = "商品を選択してください")
	private Long productId;

	@NotNull(message = "製造数量を入力してください")
	@Min(value = 1, message = "製造数量は1以上で入力してください")
	private Integer quantity;
}
