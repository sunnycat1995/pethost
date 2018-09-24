package com.project.pethost.repository;

import com.project.pethost.dbo.OrderDbo;
import com.project.pethost.dbo.OrderStatusDbo;
import com.project.pethost.dbo.UserDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderDbo, Long> {
    List<OrderDbo> findAllByStatus(final OrderStatusDbo orderStatusDbo);
    List<OrderDbo> findAllByPetOwner(final UserDbo currentUser);
}
