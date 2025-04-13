package com.fawry.order_api.sevices.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fawry.order_api.entity.Order;
import com.fawry.order_api.repos.OrderRepository;
import com.fawry.order_api.sevices.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository=orderRepository;
    }
    @Override
    public String saveOrder(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveOrder'");
    }
    @Override
    public List<Order> getOrderByCustomerEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderByCustomerEmail'");
    }
    @Override
    public List<Order> getOrdersInPeriod(LocalDate start, LocalDate end) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersInPeriod'");
    }
}