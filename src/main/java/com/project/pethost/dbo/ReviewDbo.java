package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Table(name = "review")
public class ReviewDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderDbo order;

    private String review;
    private int keeperRating;
    private int petRating;
}
