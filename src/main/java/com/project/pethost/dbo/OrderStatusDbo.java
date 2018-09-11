package com.project.pethost.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "order_status")
public enum OrderStatusDbo {
    REQUESTED(1, "Requested"),
    IN_AGREEMENT(2, "In agreenment"),
    IN_SAFE_HANDS(3, "In safe hands"),
    RESOLVED(4, "Resolved"),
    UNKNOWN(5, "Unknown");

    @Id
    private Integer id;
    private String status;
}
