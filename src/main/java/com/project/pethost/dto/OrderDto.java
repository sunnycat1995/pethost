package com.project.pethost.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String status;
    private String petId;
    private String petName;
    private String petCategory;

    private Long keeperId;
    private String keeperEmail;
    private String keeperName;
    private String[] keeperPhone;

    private Long ownerId;
    private String ownerEmail;
    private String ownerName;

    private String comments;
    private String createdDate;
    private String bookingDate;
    private String lastModifiedDate;
}