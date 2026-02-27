package com.example.foodapp.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.foodapp.entity.Material;
import com.example.foodapp.entity.Order;
import com.example.foodapp.entity.OrderStatus;
import com.example.foodapp.repository.MaterialRepository;
import com.example.foodapp.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MaterialRepository materialRepository;
	private final MaterialService materialService;

	// 発注登録
	public void createOrder(Long materialId, int quantity) {

		if (quantity <= 0) {
			throw new IllegalArgumentException("数量は0より大きい値を入力してください");
		}

		Material material = materialRepository.findById(materialId)
				.orElseThrow(() -> new IllegalArgumentException("原料が見つかりません"));

		Order order = new Order(material, quantity);

		orderRepository.save(order);
	}

	// 入荷処理
	public void receiveOrder(Long orderId) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new IllegalArgumentException("発注が見つかりません"));

		// 発注ステータス更新
		order.markAsReceived();

		// 在庫増加 + 履歴記録
		materialService.increaseStockWithHistory(
				order.getMaterial(),
				order.getOrderQuantity());
	}

	// 発注キャンセル
	public void cancelOrder(Long orderId) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new IllegalArgumentException("発注が見つかりません"));

		// 状態遷移はエンティティに任せる
		order.cancel();
	}

	// 未入荷取得
	public List<Order> getPendingOrders() {
		return orderRepository.findByStatusOrderByOrderDateDesc(OrderStatus.ORDERED);
	}

}
