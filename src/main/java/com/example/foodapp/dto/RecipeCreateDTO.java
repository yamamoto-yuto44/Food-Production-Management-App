package com.example.foodapp.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

//レシピ登録用
@Getter
@Setter
public class RecipeCreateDTO {

	@NotBlank(message = "商品名を入力してください")
	private String productName;

	@Valid
	private List<RecipeItemDTO> items = new ArrayList<>();

	public RecipeCreateDTO() {
        // 初期で1行分作る
        this.items.add(new RecipeItemDTO());
    }
}
