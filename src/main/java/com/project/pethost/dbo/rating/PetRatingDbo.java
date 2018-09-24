package com.project.pethost.dbo.rating;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "pet_rating")
public class PetRatingDbo extends RatingDbo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
