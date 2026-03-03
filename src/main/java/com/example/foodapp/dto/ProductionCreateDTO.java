package com.example.foodapp.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductionCreateDTO {
	
	@NotNull(message = "商品名を選択してください")
    private Long productId;

    @NotNull(message = "製造日を入力してください")
    @PastOrPresent(message = "製造日は今日以前の日付を入力してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate productionDate;

    @NotNull(message = "数量を入力してください")
    @Min(value = 1, message = "数量は1以上で入力してください")
    private Integer quantity;

}
