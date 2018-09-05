package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Table(name = "order")
public class OrderDbo {
    private Long id;
    private UserDbo keeper;
    private OrderStatusDbo status;
    private PetDbo pet;
    private LocalDateTime createdDate;
    private LocalDateTime bookingDate;
    private LocalDateTime lastModifiedDate;
    private String comments;
}
