package com.example.foodapp.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.foodapp.dto.ProductionCreateDTO;
import com.example.foodapp.dto.ProductionDTO;
import com.example.foodapp.dto.ProductionUpdateDTO;
import com.example.foodapp.entity.Production;
import com.example.foodapp.service.ProductService;
import com.example.foodapp.service.ProductionService;

@Controller
@RequestMapping("/productions")
public class ProductionController {

	/** DI */
	private final ProductionService productionService;
	private final ProductService productService;

	public ProductionController(ProductionService productionService,
			ProductService productService) {
		this.productionService = productionService;
		this.productService = productService;
	}

	// ======================================
	// 一覧表示
	// ======================================
	@GetMapping
	public String list(Model model) {
        List<ProductionDTO> productions = productionService.findAll();
        model.addAttribute("productions", productions);
        return "productions/list";
	}

	// ======================================
	// 新規登録画面
	// ======================================
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("production", new ProductionCreateDTO());
		model.addAttribute("products", productService.findAll());
		return "productions/create";
	}
	
	@PostMapping
	public String create(
	        @Valid @ModelAttribute("production") ProductionCreateDTO dto,
	        BindingResult bindingResult,
	        Model model) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("products", productService.findAll());
	        return "productions/create";
	    }

	    try {
	        productionService.create(dto);
	    } catch (IllegalArgumentException e) {
	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("products", productService.findAll());
	        return "productions/create";
	    }

	    return "redirect:/productions";
	}

	// 編集画面
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {

		Production production = productionService.findById(id);

		ProductionUpdateDTO dto = convertToDTO(production);

		model.addAttribute("production", dto);
		model.addAttribute("products", productService.findAll());

		return "productions/edit";
	}

	@PostMapping("/update")
	public String save(
	        @Valid @ModelAttribute("production") ProductionUpdateDTO dto,
	        BindingResult bindingResult,
	        Model model) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("products", productService.findAll());
	        return "productions/edit";   // ← 画面に戻す
	    }

	    productionService.update(dto);
	    return "redirect:/productions";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		productionService.delete(id);

		return "redirect:/productions";
	}

	private ProductionUpdateDTO convertToDTO(Production production) {
		ProductionUpdateDTO dto = new ProductionUpdateDTO();
		dto.setId(production.getId());
		dto.setProductId(production.getProduct().getId());
		dto.setProductionDate(production.getProductionDate());
		dto.setQuantity(production.getQuantity());
		return dto;
	}

}
