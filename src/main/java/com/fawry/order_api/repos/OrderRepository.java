package com.fawry.order_api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fawry.order_api.entity.Order;

public interface OrderRepository extends  JpaRepository<Order,Long> {
}