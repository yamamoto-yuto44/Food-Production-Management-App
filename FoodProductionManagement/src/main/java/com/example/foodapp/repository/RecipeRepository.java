package com.example.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.foodapp.dto.RecipeListDTO;
import com.example.foodapp.entity.Product;
import com.example.foodapp.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	//JPAのコンストラクタ式
	//DTOのコンストラクタと引数順が完全一致していないとエラーになる
	@Query("""
			    SELECT new com.example.foodapp.dto.RecipeListDTO(
			        r.id,
			        p.productName,
			        m.materialName,
			        r.requiredQuantity
			    )
			    FROM Recipe r
			    JOIN r.product p
			    JOIN r.material m
			    ORDER BY p.id
			""")
	List<RecipeListDTO> findAllRecipeList();
	
	// 生産用：特定商品のレシピ取得
    List<Recipe> findByProduct(Product product);

}
