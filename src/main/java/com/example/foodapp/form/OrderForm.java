package com.example.foodapp.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
	
	@NotNull(message = "原料を選択してください")
    private Long materialId;

    @NotNull(message = "数量を入力してください")
    @Min(value = 1, message = "数量は1以上で入力してください")
    private Integer quantity;

}
