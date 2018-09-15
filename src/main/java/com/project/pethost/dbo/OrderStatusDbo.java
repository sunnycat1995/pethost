package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "order_status")
public class OrderStatusDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String status;
}
