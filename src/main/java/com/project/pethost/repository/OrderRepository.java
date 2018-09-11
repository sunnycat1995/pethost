package com.project.pethost.repository;

import com.project.pethost.dbo.OrderDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderDbo, Long> {
    List<OrderDbo> findAllByStatus(final Integer id);
}
