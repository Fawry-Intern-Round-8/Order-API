package com.fawry.order_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fawry.order_api.entity.Order;
import com.fawry.order_api.entity.OrderDTO;
import com.fawry.order_api.sevices.OrderService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @PostMapping("create")
    public String createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setPrice(orderDTO.getPrice());
        order.setCardNumber(orderDTO.getCardNumber());
        order.setCouponCode(orderDTO.getCouponCode());
        order.setCreationDate(LocalDate.now());
        order.setProductId(orderDTO.getProductId());
        order.setQuantity(orderDTO.getQuantity());
        order.setLongitude(orderDTO.getLongitude());
        order.setLatitude(orderDTO.getLatitude());
        return orderService.saveOrder(order);
    }
    @GetMapping("/getAllOrders")
    public List<Order>getAllOrders(){
        return orderService.getOrders();
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