package com.example.foodapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//入力用
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeItemDTO {

	@NotNull(message = "原料を選択してください")
    private Long materialId;
	
	@NotNull(message = "必要数量を入力してください")
	@Min(value = 1, message = "必要数量は1以上で入力してください")
    private Integer requiredQuantity;
}
