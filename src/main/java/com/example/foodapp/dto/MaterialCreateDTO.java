package com.example.foodapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

// 原料登録用DTO
@Getter
@Setter
public class MaterialCreateDTO {

    @NotBlank(message = "原料名を入力してください")
    private String materialName;

    @NotNull(message = "初期在庫数を入力してください")
    @Min(value = 0, message = "0以上で入力してください")
    private Integer stockQuantity;
    
    @NotNull(message = "安全在庫数を入力してください")
    @Min(value = 0, message = "0以上で入力してください")
    private Integer safetyStockQuantity;
}
