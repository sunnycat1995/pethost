package com.project.pethost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatusDto {
    REQUESTED(1, "Requested"),
    IN_AGREEMENT(2, "In agreenment"),
    IN_SAFE_HANDS(3, "In safe hands"),
    RESOLVED(4, "Resolved"),
    UNKNOWN(5, "Unknown");

    private Integer id;
    private String value;
}
