package com.fawry.order_api.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fawry.order_api.entity.Order;

public interface OrderRepository extends  JpaRepository<Order,Long> {
    List<Order>findAllByEmail(String email);
    List<Order> findAllByStartAndEndDate(LocalDate start, LocalDate end);
}