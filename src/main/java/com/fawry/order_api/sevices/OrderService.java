package com.fawry.order_api.sevices;

import java.time.LocalDate;
import java.util.List;

import com.fawry.order_api.entity.Order;

public interface OrderService {
    public String saveOrder(Order order);
    public List<Order>getOrderByCustomerEmail(String email);
    public List<Order>getOrdersInPeriod(LocalDate start,LocalDate end);
    public List<Order>getOrders();
}