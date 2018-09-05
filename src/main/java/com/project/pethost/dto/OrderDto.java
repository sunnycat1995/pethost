package com.project.pethost.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private UserDto keeper;
    private OrderStatusDto status;
    private PetDto pet;
    private LocalDateTime createdDate;
    private LocalDateTime bookingDate;
    private LocalDateTime lastModifiedDate;
    private String comments;
}
