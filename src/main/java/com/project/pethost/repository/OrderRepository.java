package com.project.pethost.repository;

import com.project.pethost.dbo.OrderDbo;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderDbo, Long> {
}
