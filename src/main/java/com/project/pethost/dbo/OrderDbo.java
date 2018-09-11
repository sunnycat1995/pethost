package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order")
public class OrderDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = UserDbo.class)
    private UserDbo keeper;

    private OrderStatusDbo status;

    @OneToOne(targetEntity = PetDbo.class)
    private PetDbo pet;

    private LocalDateTime createdDate;

    private LocalDateTime bookingDate;

    private LocalDateTime lastModifiedDate;

    private String comments;
}
