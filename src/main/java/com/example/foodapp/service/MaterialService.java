package com.example.foodapp.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.foodapp.dto.MaterialCreateDTO;
import com.example.foodapp.entity.ChangeType;
import com.example.foodapp.entity.Material;
import com.example.foodapp.entity.MaterialStockHistory;
import com.example.foodapp.repository.MaterialRepository;
import com.example.foodapp.repository.MaterialStockHistoryRepository;

@Service
@Transactional
public class MaterialService {

	private final MaterialRepository materialRepository;
	private final MaterialStockHistoryRepository historyRepository;

	public MaterialService(MaterialRepository materialRepository,
			MaterialStockHistoryRepository historyRepository) {
		this.materialRepository = materialRepository;
		this.historyRepository = historyRepository;
	}

	// =========================
	// 原料一覧取得
	// =========================
	@Transactional(readOnly = true)
	public List<Material> findAll() {
		return materialRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Material findById(Long id) {
		return materialRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("原料が見つかりません"));
	}

	// =========================
	// 在庫増加（入荷）
	// =========================
	public void receive(Material material, int quantity) {

		if (quantity <= 0) {
			throw new IllegalArgumentException("数量は0より大きい値を入力してください");
		}

		material.changeStockQuantity(quantity);

		historyRepository.save(
				material.createHistory(quantity, ChangeType.RECEIVE));
	}

	// =========================
	// 在庫減少（消費）
	// =========================
	public void consume(Material material, int quantity) {

		if (quantity <= 0) {
			throw new IllegalArgumentException("数量は0より大きい値を入力してください");
		}

		if (material.getStockQuantity() < quantity) {
			throw new IllegalArgumentException("在庫が不足しています");
		}

		material.changeStockQuantity(-quantity);

		historyRepository.save(
				material.createHistory(-quantity, ChangeType.CONSUME));
	}

	// =========================
	// 修正（差分調整）
	// =========================
	public void adjust(Material material, int diff) {

		if (diff == 0) {
			return;
		}

		if (diff < 0 && material.getStockQuantity() < -diff) {
			throw new IllegalArgumentException("在庫が不足しています");
		}

		material.changeStockQuantity(diff);

		historyRepository.save(
				material.createHistory(diff, ChangeType.ADJUST));
	}

	// =========================
	// 履歴取得
	// =========================
	@Transactional(readOnly = true)
	public List<MaterialStockHistory> getHistory(Long materialId) {

		List<MaterialStockHistory> histories = historyRepository.findByMaterialIdOrderByChangeDateDesc(materialId);

		histories.sort(
				Comparator
						.comparing(MaterialStockHistory::getChangeDate)
						.reversed()
						.thenComparing(h -> getTypeOrder(h.getChangeType())));

		return histories;
	}

	private int getTypeOrder(ChangeType changeType) {
		return switch (changeType) {
		case ADJUST -> 4;
		case CONSUME -> 3;
		case RECEIVE -> 2;
		case INITIAL -> 1;
		};
	}

	public List<MaterialStockHistory> findHistoryByMaterial(Long materialId) {

		Material material = materialRepository.findById(materialId)
				.orElseThrow(() -> new IllegalArgumentException("原料が見つかりません"));

		return historyRepository
				.findByMaterialOrderByChangeDateDesc(material);
	}

	public void create(MaterialCreateDTO dto) {

		// 重複チェック
		if (materialRepository.existsByMaterialName(dto.getMaterialName())) {
			throw new IllegalArgumentException("その原料名は既に登録されています");
		}

		Material material = new Material(
				dto.getMaterialName(),
				dto.getStockQuantity(),
				dto.getSafetyStockQuantity());

		materialRepository.save(material);

		// 初期在庫履歴
		historyRepository.save(
				material.createHistory(
						dto.getStockQuantity(),
						ChangeType.INITIAL));
	}

	public void delete(Long id) {

		Material material = materialRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("原料が見つかりません"));

		if (!historyRepository.findByMaterialOrderByChangeDateDesc(material).isEmpty()) {
			throw new IllegalArgumentException("履歴が存在するため削除できません");
		}

		materialRepository.delete(material);
	}
	
	public void increaseStockWithHistory(Material material, int quantity) {

	    material.increaseStockQuantity(quantity);

	    historyRepository.save(
	        material.createHistory(
	            quantity,
	            ChangeType.RECEIVE));
	}
	
	public void order(Long materialId, Integer quantity) {
		
	}


}
