package com.example.foodapp.service;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.foodapp.dto.RecipeCreateDTO;
import com.example.foodapp.dto.RecipeDisplayItemDTO;
import com.example.foodapp.dto.RecipeGroupDTO;
import com.example.foodapp.dto.RecipeItemDTO;
import com.example.foodapp.dto.RecipeListDTO;
import com.example.foodapp.entity.Material;
import com.example.foodapp.entity.Product;
import com.example.foodapp.entity.Recipe;
import com.example.foodapp.repository.MaterialRepository;
import com.example.foodapp.repository.ProductRepository;
import com.example.foodapp.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final ProductRepository productRepository;
	private final MaterialRepository materialRepository;

	public List<RecipeListDTO> findAll() {
		return recipeRepository.findAllRecipeList();
	}

	public List<RecipeGroupDTO> findAllGrouped() {

		List<RecipeListDTO> flatList = recipeRepository.findAllRecipeList();

		Map<String, List<RecipeDisplayItemDTO>> grouped = flatList.stream()
				.collect(Collectors.groupingBy(
						RecipeListDTO::getProductName,
						LinkedHashMap::new,
						Collectors.mapping(
								r -> new RecipeDisplayItemDTO(
										r.getMaterialName(),
										r.getRequiredQuantity()),
								Collectors.toList())));

		return grouped.entrySet().stream()
				.map(e -> new RecipeGroupDTO(e.getKey(), e.getValue()))
				.toList();
	}

	@Transactional
	public void create(RecipeCreateDTO dto) {

		// ① 空行除外（material未選択など）
		List<RecipeItemDTO> validItems = dto.getItems().stream()
				.filter(item -> item.getMaterialId() != null)
				.toList();

		if (validItems.isEmpty()) {
			throw new IllegalArgumentException("原料を1つ以上選択してください");
		}

		// ② 重複チェック
		List<Long> materialIds = validItems.stream()
				.map(RecipeItemDTO::getMaterialId)
				.toList();

		Set<Long> unique = new HashSet<>(materialIds);

		if (materialIds.size() != unique.size()) {
			throw new IllegalArgumentException("同じ原料は選択できません");
		}

		// ③ 商品保存
		Product product = new Product(dto.getProductName());
		productRepository.save(product);

		// ④ レシピ保存
		for (RecipeItemDTO item : validItems) {

			Material material = materialRepository
					.findById(item.getMaterialId())
					.orElseThrow(() -> new IllegalArgumentException("原料が存在しません"));

			Recipe recipe = new Recipe(
					product,
					material,
					item.getRequiredQuantity());

			recipeRepository.save(recipe);
		}
	}

}
