package com.fawry.order.repos;

import com.fawry.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByEmail(String email);

    List<Order> findAllByStartAndEndDate(LocalDate start, LocalDate end);
}