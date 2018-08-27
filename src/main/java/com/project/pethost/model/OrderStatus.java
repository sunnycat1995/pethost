package com.project.pethost.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "order_status")
public enum OrderStatus {
    REQUESTED(1, "Requested"),
    IN_AGREEMENT(2, "In agreenment"),
    IN_SAFE_HANDS(3, "In safe hands"),
    RESOLVED(4, "Resolved"),
    UNKNOWN(5, "Unknown");

    @Id
    private Integer id;
    private String value;
}
