package com.example.foodapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.foodapp.entity.Product;
import com.example.foodapp.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

}
