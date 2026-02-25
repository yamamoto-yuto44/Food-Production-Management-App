package com.example.foodapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.foodapp.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // 発注一覧（未入荷）
    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderService.getPendingOrders());
        return "orders/list";
    }

    // 発注登録
    @PostMapping("/create")
    public String create(@RequestParam Long materialId,
                         @RequestParam int quantity) {

        orderService.createOrder(materialId, quantity);
        return "redirect:/orders";
    }

    // 入荷処理
    @PostMapping("/receive/{id}")
    public String receive(@PathVariable Long id) {

        orderService.receiveOrder(id);
        return "redirect:/orders";
    }
}

