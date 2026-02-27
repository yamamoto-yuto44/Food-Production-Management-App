package com.example.foodapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.foodapp.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final OrderService orderService;

	@GetMapping("/")
	public String home(Model model) {

		// 未入荷件数
		model.addAttribute("pendingCount",
				orderService.getPendingOrders().size());

		return "home";
	}

}
