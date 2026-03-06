package com.example.foodapp.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.foodapp.dto.ProductionCreateDTO;
import com.example.foodapp.dto.ProductionDTO;
import com.example.foodapp.dto.ProductionUpdateDTO;
import com.example.foodapp.entity.Material;
import com.example.foodapp.entity.Product;
import com.example.foodapp.entity.Production;
import com.example.foodapp.entity.Recipe;
import com.example.foodapp.repository.ProductRepository;
import com.example.foodapp.repository.ProductionRepository;
import com.example.foodapp.repository.RecipeRepository;

@Service
@Transactional
public class ProductionService {

	/** DI */
	private final ProductionRepository productionRepository;
	private final ProductRepository productRepository;
	private final RecipeRepository recipeRepository;
	private final MaterialService materialService;

	/** 依存が一目で分かる、finalにできるため、@Autowiredは使わない */
	public ProductionService(ProductionRepository productionRepository,
			ProductRepository productRepository, RecipeRepository recipeRepository,
			MaterialService materialService) {
		this.productionRepository = productionRepository;
		this.productRepository = productRepository;
		this.recipeRepository = recipeRepository;
		this.materialService = materialService;
	}

	public List<ProductionDTO> findAll() {
		return productionRepository.findAllByOrderByProductionDateDesc().stream()
				.map(this::convertToDTO)
				.toList();
	}

	public Production findById(Long id) {
		return productionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("該当の生産記録が見つかりません"));
	}

	private ProductionDTO convertToDTO(Production production) {
		ProductionDTO dto = new ProductionDTO();
		dto.setId(production.getId());
		dto.setProductId(production.getProduct().getId());
		dto.setProductName(production.getProduct().getProductName());
		dto.setProductionDate(production.getProductionDate());
		dto.setQuantity(production.getQuantity());
		return dto;
	}

	//新規登録
	public void create(ProductionCreateDTO dto) {

		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("製品が見つかりません"));

		Production production = new Production(
				product,
				dto.getProductionDate(),
				dto.getQuantity());

		productionRepository.save(production);

		reduceStock(product, dto.getQuantity());

	}

	public void update(ProductionUpdateDTO dto) {

		Production production = productionRepository.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("生産データが見つかりません"));

		Product beforeProduct = production.getProduct();
		int beforeQuantity = production.getQuantity();

		Product afterProduct = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("製品が見つかりません"));

		int afterQuantity = dto.getQuantity();

		// ① before分を戻す
		restoreStock(beforeProduct, beforeQuantity);

		// ② after分を消費
		reduceStock(afterProduct, afterQuantity);

		// =========================
		// ③ Production更新
		// =========================
		production.update(
				afterProduct,
				dto.getProductionDate(),
				afterQuantity);
	}

	public void delete(Long productionId) {

		// ① 削除対象取得
		Production production = productionRepository.findById(productionId)
				.orElseThrow(() -> new RuntimeException("生産データが見つかりません"));

		Product product = production.getProduct();
		int quantity = production.getQuantity();

		// ② 在庫を戻す
		restoreStock(product, quantity);

		// ③ 削除
		productionRepository.delete(production);
	}

	public Production save(ProductionDTO dto) {
		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new IllegalArgumentException("指定された商品が存在しません: " + dto.getProductId()));

		if (dto.getId() == null) {
			// 新規登録
			Production production = new Production(product, dto.getProductionDate(), dto.getQuantity());
			return productionRepository.save(production);
		} else {
			// 更新
			Production production = findById(dto.getId());
			production.update(product, dto.getProductionDate(), dto.getQuantity());
			return production; // @Transactional により自動更新
		}
	}

	private void reduceStock(Product product, int quantity) {

		List<Recipe> recipes = recipeRepository.findByProduct(product);

		// ① 先に不足チェック
		for (Recipe recipe : recipes) {

			int totalAmount = recipe.getRequiredQuantity() * quantity;

			Material material = recipe.getMaterial();

			if (material.getStockQuantity() < totalAmount) {
				throw new IllegalArgumentException(
						material.getMaterialName() + " の在庫が不足しています");
			}
		}

		// ② 問題なければ減算
		for (Recipe recipe : recipes) {

			int totalAmount = recipe.getRequiredQuantity() * quantity;

			materialService.consume(
					recipe.getMaterial(),
					totalAmount);
		}
	}

	private void restoreStock(Product product, int quantity) {

		List<Recipe> recipes = recipeRepository.findByProduct(product);

		for (Recipe recipe : recipes) {

			int totalAmount = recipe.getRequiredQuantity() * quantity;

			materialService.adjust(
					recipe.getMaterial(),
					totalAmount // プラスで戻す
			);
		}
	}
}
