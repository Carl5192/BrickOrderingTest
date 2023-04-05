package com.caci.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caci.demo.model.Order;
import com.caci.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<String> createOrder(@RequestBody int numberOfBricks) {
		Order order = orderService.createOrder(numberOfBricks);
		return ResponseEntity.ok(order.getOrderReference());
	}

	@GetMapping("/{orderReference}")
	public ResponseEntity<Order> getOrder(@PathVariable String orderReference) {
		Optional<Order> optionalOrder = orderService.getOrder(orderReference);
		return optionalOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

	@PutMapping("/{orderReference}")
	public ResponseEntity<Order> updateOrder(@PathVariable String orderReference, @RequestBody int numberOfBricks) {
		Optional<Order> optionalOrder = orderService.updateOrder(orderReference, numberOfBricks);
		if (optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			if (order.isDispatched()) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok(order);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{orderReference}/fulfil")
	public ResponseEntity<Void> fulfilOrder(@PathVariable String orderReference) {
		boolean fulfilled = orderService.fulfilOrder(orderReference);
		if (fulfilled) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
