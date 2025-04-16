package com.fawry.order.sevices;

import java.time.LocalDate;
import java.util.List;

import com.fawry.order.entity.Order;

public interface OrderService {
    public String saveOrder(Order order);
    public List<Order>getOrderByCustomerEmail(String email);
    public List<Order>getOrdersInPeriod(LocalDate start,LocalDate end);
}