package com.caci.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.caci.demo.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
	Optional<Order> findByOrderReference(String orderRef);
}