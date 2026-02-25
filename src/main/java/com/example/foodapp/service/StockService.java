package com.example.foodapp.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.foodapp.entity.ChangeType;
import com.example.foodapp.entity.Material;
import com.example.foodapp.entity.MaterialStockHistory;
import com.example.foodapp.repository.MaterialRepository;
import com.example.foodapp.repository.MaterialStockHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final MaterialRepository materialRepository;
    private final MaterialStockHistoryRepository historyRepository;

    @Transactional
    public void adjustStock(Long materialId,
                            int changeQuantity,
                            ChangeType changeType) {

        Material material = materialRepository.findById(materialId)
                .orElseThrow();

        int newStockQuantity = material.getStockQuantity() + changeQuantity;

        if (newStockQuantity < 0) {
            throw new IllegalArgumentException("在庫不足です");
        }

        material.changeStockQuantity(newStockQuantity); // setterじゃなく業務メソッド

        MaterialStockHistory history =
                new MaterialStockHistory(
                        material,
                        changeQuantity,
                        changeType
                );

        historyRepository.save(history);
    }
}
