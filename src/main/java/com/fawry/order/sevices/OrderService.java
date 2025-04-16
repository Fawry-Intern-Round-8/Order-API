package com.fawry.order.sevices;

import com.fawry.order.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public String saveOrder(Order order);

    public List<Order> getOrderByCustomerEmail(String email);

    public List<Order> getOrdersInPeriod(LocalDate start, LocalDate end);
}