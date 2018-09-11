package com.project.pethost.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order")
@NoArgsConstructor
public class OrderDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "keeper_id")
    private UserDbo keeper;

    @OneToOne
    @JoinColumn(name = "status_id")
    private OrderStatusDbo status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id")
    private PetDbo pet;

    private LocalDateTime createdDate;

    private LocalDateTime bookingDate;

    private LocalDateTime lastModifiedDate;

    private String comments;
}
