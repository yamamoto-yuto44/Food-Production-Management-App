package com.example.foodapp.dto;

import lombok.Getter;
import lombok.Setter;

//表示専用DTO
@Getter
@Setter
public class RecipeDisplayItemDTO {

	private String materialName;
	private Integer requiredQuantity;

	public RecipeDisplayItemDTO(String materialName, Integer requiredQuantity) {
		this.materialName = materialName;
		this.requiredQuantity = requiredQuantity;
	}

	
}
