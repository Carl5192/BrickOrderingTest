package com.caci.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caci.demo.model.Order;
import com.caci.demo.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(int numberOfBricks) {
    	if (numberOfBricks < 1) {
            throw new IllegalArgumentException("Number of bricks must be greater than 0");
        }
 
        Order order = new Order();
        order.setNumberOfBricks(numberOfBricks);
        order = orderRepository.save(order);
        order.setOrderReference(UUID.randomUUID().toString());
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(String orderReference) {
        return orderRepository.findByOrderReference(orderReference);
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Optional<Order> updateOrder(String orderReference, int numberOfBricks) {
    	if (numberOfBricks < 1) {
            throw new IllegalArgumentException("Number of bricks must be greater than 0");
        }
    	
        Optional<Order> optionalOrder = getOrder(orderReference);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setNumberOfBricks(numberOfBricks);
            order = orderRepository.save(order);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public boolean fulfilOrder(String orderReference) {
        Optional<Order> optionalOrder = getOrder(orderReference);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.isDispatched()) {
                order.setDispatched(true);
                orderRepository.save(order);
                return true;
            }
        }
        return false;
    }
}
