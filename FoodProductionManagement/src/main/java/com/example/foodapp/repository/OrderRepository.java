package com.example.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.entity.Order;
import com.example.foodapp.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByStatusOrderByOrderDateDesc(OrderStatus status);
}
