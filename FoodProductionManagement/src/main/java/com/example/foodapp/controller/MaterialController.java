package com.example.foodapp.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.foodapp.dto.MaterialCreateDTO;
import com.example.foodapp.entity.Material;
import com.example.foodapp.entity.MaterialStockHistory;
import com.example.foodapp.service.MaterialService;

@Controller
public class MaterialController {

	private final MaterialService materialService;

	public MaterialController(MaterialService materialService) {
		this.materialService = materialService;
	}

	// 原料一覧表示
	@GetMapping("/materials")
	public String list(Model model) {

		List<Material> materials = materialService.findAll();

		model.addAttribute("materials", materials);

		return "materials/list";
	}

	@GetMapping("/materials/{id}")
	public String detail(@PathVariable Long id, Model model) {

		Material material = materialService.findById(id);

		List<MaterialStockHistory> historyList = materialService.getHistory(id);

		model.addAttribute("material", material);
		model.addAttribute("historyList", historyList);

		return "materials/detail";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArgument(IllegalArgumentException e, Model model) {

		model.addAttribute("errorMessage", e.getMessage());

		// 一覧に戻す
		List<Material> materials = materialService.findAll();
		model.addAttribute("materials", materials);

		return "materials/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("materialCreateDTO", new MaterialCreateDTO());
		return "materials/create";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute MaterialCreateDTO dto,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "materials/create";
		}

		materialService.create(dto);

		return "redirect:/materials";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		materialService.delete(id);
		return "redirect:/materials";
	}

}
