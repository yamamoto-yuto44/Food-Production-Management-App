package com.example.foodapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//一覧表示用
@Getter
@Setter
@AllArgsConstructor
public class RecipeGroupDTO {

    private String productName;
    private List<RecipeDisplayItemDTO> items;
}
