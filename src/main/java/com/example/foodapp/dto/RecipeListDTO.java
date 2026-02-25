package com.example.foodapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 一覧表示用（DB検索結果を受け取るDTO）
@Getter
@Setter
@AllArgsConstructor
public class RecipeListDTO {
	
	private Long recipeId;
    private String productName;
    private String materialName;
    private Integer requiredQuantity;

}
