package com.project.pethost.repository;

import com.project.pethost.dbo.OrderStatusDbo;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatusDbo, Integer> {
    OrderStatusDbo findByStatus(final String status);
}
