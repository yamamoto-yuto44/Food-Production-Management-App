package com.example.foodapp.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.foodapp.form.OrderForm;
import com.example.foodapp.service.MaterialService;
import com.example.foodapp.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	private final MaterialService materialService;

	// 発注一覧（未入荷）
	@GetMapping
	public String list(Model model) {
		model.addAttribute("orderForm", new OrderForm());
		model.addAttribute("orders", orderService.getPendingOrders());
		model.addAttribute("materials", materialService.findAll());
		return "orders/list";
	}

	// 発注登録
	@PostMapping("/create")
	public String createOrder(
			@Valid @ModelAttribute("orderForm") OrderForm form,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("orders", orderService.getPendingOrders());
			model.addAttribute("materials", materialService.findAll());
			return "orders/list";
		}

		orderService.createOrder(form.getMaterialId(), form.getQuantity());
		return "redirect:/orders";
	}

	// 入荷処理
	@PostMapping("/receive/{id}")
	public String receive(@PathVariable Long id) {

		orderService.receiveOrder(id);
		return "redirect:/orders";
	}

	// キャンセル処理
	@PostMapping("/cancel/{id}")
	public String cancel(@PathVariable Long id) {

		orderService.cancelOrder(id);

		return "redirect:/orders";
	}
}
