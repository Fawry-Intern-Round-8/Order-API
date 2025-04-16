package com.fawry.order.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fawry.order.entity.Order;
import com.fawry.order.sevices.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @PostMapping("create")
    public Order createOrder(@RequestBody Order Order) {
        this.orderService.saveOrder(Order);
        return Order;
    }
    
    @GetMapping("")
    public List<Order> getOrdersInPeriod(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return this.orderService.getOrdersInPeriod(start, end);
    }

    @GetMapping("/user")
    public List<Order> getOrderByEmail(@RequestParam String customerEmail) {
        return this.orderService.getOrderByCustomerEmail(customerEmail);
    }
    
}