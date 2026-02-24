package com.example.foodapp.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.foodapp.dto.RecipeCreateDTO;
import com.example.foodapp.dto.RecipeItemDTO;
import com.example.foodapp.service.MaterialService;
import com.example.foodapp.service.RecipeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

	private final RecipeService recipeService;
	private final MaterialService materialService;

	// 一覧
	@GetMapping
	public String list(Model model) {
		model.addAttribute("recipes", recipeService.findAllGrouped());
		return "recipes/list";
	}

	// 登録
	@GetMapping("/create")
	public String showCreateForm(Model model) {

		RecipeCreateDTO dto = new RecipeCreateDTO();

		model.addAttribute("recipeCreateDTO", dto);
		model.addAttribute("materials", materialService.findAll());

		return "recipes/create";
	}

	// 追加ボタン処理
	@PostMapping(params = "addItem")
	public String addItem(@ModelAttribute("recipeCreateDTO") RecipeCreateDTO dto, BindingResult result, Model model) {

		dto.getItems().add(new RecipeItemDTO());

		model.addAttribute("materials", materialService.findAll());

		return "recipes/create";
	}

	// 削除ボタン処理
	@PostMapping(params = "removeIndex")
	public String removeItem(@RequestParam int removeIndex,
			@ModelAttribute("recipeCreateDTO") RecipeCreateDTO dto, BindingResult result, Model model) {
		
		if (removeIndex >= 0 && removeIndex < dto.getItems().size()) {
			if (dto.getItems().size() > 1) {
				dto.getItems().remove(removeIndex);
			}
		}

		model.addAttribute("materials", materialService.findAll());

		return "recipes/create";
	}

	// 登録ボタン処理
	@PostMapping
	public String create(@Valid RecipeCreateDTO dto,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("materials", materialService.findAll());
			return "recipes/create";
		}

		try {
			recipeService.create(dto);
		} catch (IllegalArgumentException e) {

			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute("materials", materialService.findAll());
			return "recipes/create";
		}

		return "redirect:/recipes";
	}

}
