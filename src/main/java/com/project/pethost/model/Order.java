package com.project.pethost.model;

import lombok.Data;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Table(name = "order")
public class Order {
    private Long id;
    private Person keeper;
    private OrderStatus status;
    private Pet pet;
    private LocalDateTime createdDate;
    private LocalDateTime bookingDate;
    private LocalDateTime lastModifiedDate;
    private String comments;
}
