package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "rating")
public class RatingDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double rating;
    private Long counter;
}
