package com.caci.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.caci.demo.model.Order;
import com.caci.demo.repository.OrderRepository;
import com.caci.demo.service.OrderService;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void testCreateOrder() {
        int numberOfBricks = 100;
        Order expectedOrder = new Order();
        expectedOrder.setNumberOfBricks(numberOfBricks);
        expectedOrder.setOrderReference("test-ref-123");
        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);

        Order actualOrder = orderService.createOrder(numberOfBricks);

        assertEquals(expectedOrder, actualOrder);
        assertNotNull(actualOrder.getOrderReference());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateOrderWithInvalidNumberOfBricks() {
        int numberOfBricks = 0;
        orderService.createOrder(numberOfBricks);
    }

    @Test
    public void testGetOrder() {
        String orderReference = "test-ref-123";
        Order expectedOrder = new Order();
        expectedOrder.setOrderReference(orderReference);
        when(orderRepository.findByOrderReference(orderReference)).thenReturn(Optional.of(expectedOrder));

        Optional<Order> actualOrder = orderService.getOrder(orderReference);

        assertTrue(actualOrder.isPresent());
        assertEquals(expectedOrder, actualOrder.get());
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> expectedOrders = Arrays.asList(order1, order2);
        when(orderRepository.findAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getAllOrders();

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testUpdateOrder() {
        String orderReference = "test-ref-123";
        int newNumberOfBricks = 200;
        Order existingOrder = new Order();
        existingOrder.setOrderReference(orderReference);
        existingOrder.setNumberOfBricks(100);
        Order updatedOrder = new Order();
        updatedOrder.setOrderReference(orderReference);
        updatedOrder.setNumberOfBricks(newNumberOfBricks);
        when(orderRepository.findByOrderReference(orderReference)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(updatedOrder);

        Optional<Order> actualOrder = orderService.updateOrder(orderReference, newNumberOfBricks);

        assertTrue(actualOrder.isPresent());
        assertEquals(updatedOrder, actualOrder.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateOrderWithInvalidNumberOfBricks() {
        String orderReference = "test-ref-123";
        int newNumberOfBricks = 0;
        orderService.updateOrder(orderReference, newNumberOfBricks);
    }

    @Test
    public void testFulfilOrder() {
        String orderReference = "test-ref-123";
        Order existingOrder = new Order();
        existingOrder.setOrderReference(orderReference);
        existingOrder.setDispatched(false);
        when(orderRepository.findByOrderReference(orderReference)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        boolean actualResult = orderService.fulfilOrder(orderReference);

        assertTrue(actualResult);
        assertTrue(existingOrder.isDispatched());
    }
    
}
