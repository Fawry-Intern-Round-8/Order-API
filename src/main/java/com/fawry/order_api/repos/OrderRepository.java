package com.fawry.order_api.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fawry.order_api.entity.Order;

public interface OrderRepository extends  JpaRepository<Order,Long> {
    List<Order> findAllByCustomerEmail(String email);
     List<Order> findAllByCreationDateBetween(LocalDate start, LocalDate end);
}